<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/resources/css/upload.css" type="text/css" rel="stylesheet"> 
<title>uploadAjax</title>
<body>
	<div class="bigPictureWrapper">
		<div class="bigPicture"></div>
	</div>
	<h1>Ajax를 이용한 파일 업로드</h1>
	<div class="uploadDiv">
	<input type="file" name="uploadFile" multiple/>
	</div>
	<button id="uploadBtn">Upload</button>
	<div class="uploadResult">
		<ul>
		</ul>
	</div>


	<!-- jquery 사용-by jquery CDN -->
	<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
	
	<script>	
	function showImage(fileCallPath) { //이미지 출력
		//alert(fileCallPath);
		
		$(".bigPictureWrapper").css("display", "flex").show();
		//$(".bigPictureWrapper").show();
		
		$(".bigPicture")
		  .html("<img src='/display?fileName="+ encodeURI(fileCallPath)+"'>")
		  .animate({width:'100%', height: '100%'}, 1000);
		
		$(".bigPictureWrapper").on("click", function(e) {
			$(".bigPicture").animate({width:'0%', height:'0%'},1000);
			setTimeout(function() {
				$(".bigPictureWrapper").hide();
			}, 1000);
		}); //--function(e)
	} //--function showImage
	
	/* 업로드 파일 삭제 */
	$(".uploadResult").on("click", "span", function(e) {
			var targetFile = $(this).data("file");
			var type = $(this).data("type");
			console.log(targetFile);
			
			$.ajax({
				url: '/deleteFile',
				data: {fileName: targetFile, type: type},
				dataType: 'text',
				type: 'post',
				success: function(result) {
					alert(result);					
				}
			}); //--ajax
		});

	$(document).ready(function() {
		
		var cloneObj = $(".uploadDiv").clone(); //초기화용 빈객체
		
		$("#uploadBtn").on("click", function(e) {
			var formData = new FormData();
			var inputFile = $("input[name='uploadFile']");
			var files = inputFile[0].files;
			console.log(files);
			console.log(formData);
			
			for(var i=0; i < files.length; i++){
				if(!checkExtention(files[i].name, files[i].size)){
					return false;
				}
				
				formData.append("uploadFile", files[i]); 
				//uploadFile->Controller에서 받는 MultipartFile[] 변수명과 동일해야 파일이 전달된다!!
			}
			
			$.ajax({			
				url: '/uploadAjaxAction',	
				enctype: 'multipart/form-data',
				processData: false,
				contentType: false,
				data : formData,
				type: 'POST',
				dataType: 'json',
				success: function(result) {
					console.log(result);
					showUploadedFile(result);
					$(".uploadDiv").html(cloneObj.html()); //초기화
				}
			}); //--ajax
			
		}); //--function(e)
		
		/* 확장자, 크기 사전처리 */
		var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
		var maxSize = 5242880; //5MB
		
		function checkExtention(fileName, fileSize) {
			if(fileSize >= maxSize){
				alert("파일 사이즈 초과");
				return false;
			}
			if(regex.test(fileName)){
				alert("해당 종류의 파일은 업로드 할 수 없습니다.");
				return false;
			}
			return true;
		} //--function checkExtention()
		
		/* 업로드결과 출력 + 다운로드*/
		var uploadResult = $(".uploadResult ul");
		function showUploadedFile(uploadResultArr) {
			var str = "";
			$(uploadResultArr).each(function(i, obj) {
				if(!obj.image){ //이미지 파일이 아니면
					var fileCallPath = encodeURIComponent(obj.uploadPath + "/"+ obj.uuid + "_" + obj.fileName);
				
					var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
				
					
					str += "<li><a href ='/download?fileName="+fileCallPath+"'>"
							+"<img src = '/resources/img/attach.png'>"+obj.fileName+"</a>"+"<span data-file=\'"+fileCallPath+"\' data-type='file'> x </span>"+"</li>";
				}else{
					//str += "<li>"+obj.fileName+"</li>";
					var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_"+ obj.uuid + "_" + obj.fileName);
					
					var originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
					originPath = originPath.replace(new RegExp(/\\/g), "/");
					
					str += "<li><a href=\"javascript:showImage(\'"+originPath+"\')\"><img src = '/display?fileName="+fileCallPath+"'></a>"+"<span data-file=\'"+fileCallPath+"\' data-type='image'> x </span>"+"</li>";
				}
								
			});
			uploadResult.append(str);
		} //--function showUploadedFile(uploadResultArr)
		
		

	}); //--$(document).ready

	</script>

</body>
</html>