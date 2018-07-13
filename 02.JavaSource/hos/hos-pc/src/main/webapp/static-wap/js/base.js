window.addEventListener(('orientationchange' in window ? 'orientationchange' : 'resize'), 
(function () { function c() { var d = document.documentElement; var cw = d.clientWidth || 1200; 
d.style.fontSize = (20 * (cw / 600)) > 40 ? 40 + 'px' : (20 * (cw / 600)) + 'px'; } c(); 
return c; })(), false);

function checkValue(name,getAjax){
	var msg='';
	var _this=$('[name = '+name+']');
	var val=_this.val();
	var mobile = /^((1[1-9]{1}[0-9]))\d{8}$/;
	var pwd = /^(?![0-9]+$)(?![a-zA-Z]+$)(?![~!@#$%^&*()_]+$)[0-9A-Za-z~!@#$%^&*()_]{6,18}$/;
	var result=false;
	if(val==''){
		switch(name)
		{
		    case 'phone':
		        msg='${common30}';	
		        break;
		    case 'randomcode':
		        msg='${common31}';	
		        break;
		    case 'phonecode':
		        msg='${common32}';	
		        break;
		    case 'password':
		        msg='${common33}';	
		        break; 
		    case 'invitecode':
		        result=true;
		        break;    
		}
		if(!result){
			_this.addClass('errorInput');
			console.log('${common30}')
			_this.nextAll('.errorMsg').html(msg);
		}
	}else{
		switch(name)
		{
		    case 'phone':
		    	if(mobile.test(val)){
		    		if(getAjax){
		    			if(val=='18268048237'){
				    		msg='${common34}';	
				    		_this.addClass('errorInput');
							_this.nextAll('.errorMsg').html(msg);
				    	}else{
				    		result=true;
				    	}
		    		}else{
		    			result=true;
		    		}
		    	}else{
		    		msg='${common35}';	
		    		_this.addClass('errorInput');
					_this.nextAll('.errorMsg').html(msg);
		    	}
		        break;
		    case 'randomcode':
		        if(getAjax){
	    			if(getImgcode(val)){
			    		msg='{common36}';	
			    		_this.addClass('errorInput');
						_this.nextAll('.errorMsg').html(msg);
			    	}else{
			    		result=true;
			    	}
	    		}else{
	    			result=true;
	    		}
		        break;
		    case 'phonecode':
		        if(getAjax){
	    			if(val!='8888'){
			    		msg='${common37}';	
			    		_this.addClass('errorInput');
						_this.nextAll('.errorMsg').html(msg);
			    	}else{
			    		result=true;
			    	}
	    		}else{
	    			result=true;
	    		}
		        break;    
		    case 'password':
		    	if(pwd.test(val)){
		    		result=true;
		    	}else{
		    		msg='${common38}';	
		    		_this.addClass('errorInput');
					_this.nextAll('.errorMsg').html(msg);
		    	}	
		        break;
		    case 'invitecode':
		    	if(getAjax){
	    			if(val!='8888'){
			    		msg='${common39}';	
			    		_this.addClass('errorInput');
						_this.nextAll('.errorMsg').html(msg);
			    	}else{
			    		result=true;
			    	}
	    		}
		        break;    
		}
	}
	
	return result
}

function copyTxt(btn,txt){
	var clipboard = new ClipboardJS(btn, {
        target: function() {
            return document.querySelector(txt);
        }
   	});
    clipboard.on('success', function(e) {
        ck.msg({'content':'${common40}','time':1,'shade':false});
    });
    clipboard.on('error', function(e) {
        ck.msg({'content':'${common41}','time':1,'shade':false});
    });
}



$(function(){
	$('.lanNavBox .select').click(function(){
		if($(this).next('.lanLink:visible').length>0){
			$(this).next('.lanLink').hide();
		}else{
			$(this).next('.lanLink').show();
		}
	})
	$('.rightBtn').click(function(){
		$('.popNav').addClass('openNav');
	});
	$('.clickBg').click(function(){
		$('.popNav').removeClass('openNav');
	});
})
