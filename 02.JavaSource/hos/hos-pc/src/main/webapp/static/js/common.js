function getpass(){
	$("#loginForm").validate().element($("#phone"));
	$("#loginForm").validate().element($("#valicode"));
	var countDown = 60;		
	$.ajax({  
         url: "/enroll/validationcode?phone=" + $('#phone').val() + "&valicode=" + $('#valicode').val(),
         type: 'POST',  
         //data: form,  
         async: true,  
         cache: false,  
         contentType: false,  
         processData: false,  
         success: function (data) {  
        	 console.log(data);
        	 data=JSON.parse(data);
        	 if(data.errorCode == '0000'){
        		 console.log(data.errorMsg);
        		$('.checktime').html(countDown + '${common17}').css({"background":"#0e1049","color":"#666"}).prop("disabled",true);
				var timer = setInterval(function() {
					countDown--;
					if (countDown) {
						$('.checktime').html(countDown + '${common17}');
					} else {
						clearInterval(timer);
						$('.checktime').css({"background":"transparent","color":"#fff"}).html('${common8}').prop("disabled",false).bind("click",getpass);
					}
				}, 1000);
        	 }else{
        	 	$('#codeImg').trigger('click');
        	 	console.log(data.errorCode);
        	 	if(data.errorCode == '9998'){
        	 		$(".tips").html('${common30}');
        	 	}else if(data.errorCode == '9997'){
        	 		$(".tips").html('${common31}');
        	 	}else if(data.errorCode == '9996'){
        	 		$(".tips").html('${common32}');
        	 	}else if(data.errorCode == '9995'){
        	 		$(".tips").html('${common33}');
        	 	}else if(data.errorCode == '9994'){
        	 		$(".tips").html('${common34}');
        	 	}else if(data.errorCode == '9993'){
        	 		$(".tips").html('${common35}');
        	 	}else if(data.errorCode == '9992'){
        	 		$(".tips").html('${common36}');
        	 	}else if(data.errorCode == '9991'){
        	 		$(".tips").html('${common37}');
        	 	}else if(data.errorCode == '9990'){
        	 		$(".tips").html('${common38}');
        	 	}else if(data.errorCode == '9989'){
        	 		$(".tips").html('${common39}');
        	 	}else if(data.errorCode == '9988'){
        	 		$(".tips").html('${common18_1}');
        	 	}else if(data.errorCode == '9987'){
        	 		$(".tips").html('${common14}');
        	 	}else{
        	 		$(".tips").html("Exception of Request");
        	 	}
        	 }
         },  
         error: function (data) {  
        	  
         }  
   });			
}	