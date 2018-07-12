var page=1;
var len=100;
var size=0;
function creatPage(total,limit){
	var html='';
	len=Math.ceil(total/limit);
	size=limit;
	if(len<=1){
		$('.assetPage').hide();
	}else if(len>1 && len<=7){
		for(i=1;i<=len;i++){
			if(i==1){
				html+='<span class="active" onclick=toPage('+i+','+len+')>'+i+'</span>';
			}else{
				html+='<span onclick=toPage('+i+','+len+')>'+i+'</span>';
			}
		}
		$('#showPage').empty();
		$('#showPage').append(html);
	}else{
		for(i=1;i<=6;i++){
			if(i==1){
				html+='<span class="active" onclick=toPage('+i+','+len+')>'+i+'</span>';
			}else{
				html+='<span onclick=toPage('+i+','+len+')>'+i+'</span>';
			}
		}
		html+='<span onclick="nextFn()" class="dig toNext"></span><span onclick=toPage('+len+','+len+')>'+len+'</span>';
		$('#showPage').empty();
		$('#showPage').append(html);
	}
	
}
function toPage(num,len){
	var html='';
	page = num;
	$('#pre').prop('disabled',num<=1);
	$('#next').prop('disabled',num>=len);
	if(len<=7){
		$('#showPage span').eq(num-1).addClass('active').siblings().removeClass('active');
	}else{
		if((num-1)>=4 && (len-num)>=4){
			html+='<span onclick=toPage(1,'+len+')>1</span><span onclick="preFn()" class="dig toPre"></span>';
			for(i=(num-2);i<=(num+2);i++){
				if(num==i){
					html+='<span class="active" onclick=toPage('+i+','+len+')>'+i+'</span>';
				}else{
					html+='<span onclick=toPage('+i+','+len+')>'+i+'</span>';
				}
			}
			html+='<span onclick="nextFn()" class="dig toNext"></span><span onclick=toPage('+len+','+len+')>'+len+'</span>';
			$('#showPage').empty();
			$('#showPage').append(html);
		}
		if((num-1)<4){
			for(i=1;i<=6;i++){
				if(num==i){
					html+='<span class="active" onclick=toPage('+i+','+len+')>'+i+'</span>';
				}else{
					html+='<span onclick=toPage('+i+','+len+')>'+i+'</span>';
				}
			}
			html+='<span onclick="nextFn()" class="dig toNext"></span><span onclick=toPage('+len+','+len+')>'+len+'</span>';
			$('#showPage').empty();
			$('#showPage').append(html);
		}
		if((len-num)<4){
			html+='<span onclick=toPage('+i+','+len+')>1</span><span onclick="preFn()" class="dig toPre"></span>';
			for(i=(len-5);i<=len;i++){
				if(num==i){
					html+='<span class="active" onclick=toPage('+i+','+len+')>'+i+'</span>';
				}else{
					html+='<span onclick=toPage('+i+','+len+')>'+i+'</span>';
				}
			}
			$('#showPage').empty();
			$('#showPage').append(html);
		}
	}
	getData(num,size);
}
function preFn(obj){
	$(obj).prop('disabled',(page-1)<=1);
	toPage((page-1),len)
}
function nextFn(obj){
	$(obj).prop('disabled',(page+1)>=len);
	toPage((page+1),len)
}