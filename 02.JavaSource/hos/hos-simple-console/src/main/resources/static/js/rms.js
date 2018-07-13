var okCloseIndex;
/**
 * 打开窗口
 * @param title 窗体标题
 * @param url 窗体URL
 */
function openWindow(title, url,args) {
    var content = new Array();
    content.push(url);
    var wh=args?args:['600px', '400px'];
    //content.push('yes');	
    parent.layer.open({
        type: 2,
        title: title,
        maxmin: false,
        shadeClose: false,
        //点击遮罩关闭层
        area: wh,
        content: content
    });
}
/**
	 * 按钮打开窗口，如果是修改或者删除，需要传入id
	 * @param title 窗体标题
	 * @param url 窗体URL
	 * @icon 主要是有add与update 
	 * @args 参数
	 */
function openButtonWindow(title, url, icon, args) {
    if (icon != 'add') {
    	if(!checkOneIdUpdate(icon)){
			return;
		}
    	if(icon.indexOf("href") != -1){
			if(!checkOneIdUpdate(icon)){
				return;
			}
			hrefFunction(url,icon);
			return;
		}
        if (url.indexOf('?') != -1) {
            url = url + '&id=' + getSelectId()
        } else {
            url = url + '?id=' + getSelectId();
        }
    }
    openWindow(title, url,args);
}

/**
	 * 按钮点击跳页面链接
	 * @icon 主要是有add与update 
	 */
function openLocation(url,icon){
	
	if(icon!='add'){
		if(!checkOneIdUpdate(icon)){
			return;
		}
		if (url.indexOf('?') != -1) {
	        url = url + '&id=' + getSelectId()
	    } else {
	        url = url + '?id=' + getSelectId();
	    }
    }
    window.location = url;
}

function checkOneIdUpdate(icon){
	var data = $('#table').bootstrapTable('getAllSelections');	
	if(data == undefined || data.length <= 0){
		parent.layer.alert('请选择数据');
		return false;
	}else if(data.length>1){
		parent.layer.alert('只能选择一条数据');
		return false;
	}else{
		return true;
	}
}
function checkOne(data){
	if(data == undefined || data.length <= 0){
		parent.layer.alert('请选择数据');
		return false;
	}else if(data.length>1){
		parent.layer.alert('只能选择一条数据');
		return false;
	}else{
		return true;
	}
}

/*获取选中行Id*/
function getSelectId(){
	var data = $('#table').bootstrapTable('getAllSelections');
	return data[0].id;
}

function unixToDate(unix) {
    if (unix) {
        var timeTrans = new Date(parseInt(unix) * 1000);
        var timeStamp = timeTrans.toLocaleString('chinese', {
            hour12: false
        });
        var date = timeStamp.split(" ")[0].split("/");
        var d = date[0] + "-";
        if (parseInt(date[1]) > 10) {
            d += date[1] + "-";
        } else {
            d += "0" + date[1] + "-";
        }
        if (parseInt(date[2]) > 10) {
            d += date[2] + " ";
        } else {
            d += "0" + date[2] + " ";
        }
        //return date;
        return d + timeStamp.split(" ")[1];
    } else {
        return '';
    }
}

function jsonFormSubmit(url, _data) {
	var index;
    $.ajax({
        data: _data,
        url: url,
        type: 'post',
        //contentType : 'application/json;charset=utf-8',
        dataType: "json",
        success: jsonWapSuccessCallback,
        beforeSend: function() {
            index=layer.load(0, {shade: [0.1,'#fff']});
        },
        complete: function(){
        	layer.close(index)
        },
        error: function() {
            layer.alert('通讯异常！');
        }
    });
}

function jsonWapSuccessCallback(result) {
    if (result.errorCode == '7777') {
        layer.alert(result.errorMsg,
        function(index) {
            parent.location = '/admin/Y2tqcl9jZnM.html';
        });
    } else {
        jsonSuccessCallback(result);
    }
}

function jsonSuccessCallback(result) {
    glResult = result;
    layer.alert(result.errorMsg,
    function(index) {
        if (glResult.errorCode == '0000') {
            //parent.jqGridSearch();
            initTree();
            layer.close(index);
            layer.close(okCloseIndex);
            var treeObj = $.fn.zTree.getZTreeObj("deptTree");
            $("#submit").css('display', 'none');
        } else {
            initTree();
            var treeObj = $.fn.zTree.getZTreeObj("deptTree");
            layer.close(index);
            layer.close(okCloseIndex);
            $("#submit").css('display', 'none');
        }
    });
}

function refreshTable(){
	$('#table').bootstrapTable('refresh');
}

/*检查表单是否有必填字段为空*/
function checkNull(){
	var falg=true;
	var radioName,checkboxName;
	$('[require=true]').each(function(i){
		var label = $(this).parents('li').find(".title");
		if($(this).attr('type')=='radio' && $(this).attr('name')!=radioName){
			radioName=$(this).attr('name');
			var emRadio=$('input[name='+$(this).attr("name")+']');
			var isRadio=false;
			emRadio.each(function(){
				if($(this).is(':checked')){
					isRadio=true;
					return false;
				}
			})
			if(!isRadio){
				layer.tips(label.text() + '为必选项！', $(this), {
					tipsMore : true,
                    time: 10 * 500,
                    tips: 1
                });
				falg=false;
			}
		}else if($(this).attr('type')=='checkbox' && $(this).attr('name')!=checkboxName){
			checkboxName=$(this).attr('name');
			var emCheckbox=$('input[name='+$(this).attr("name")+']');
			var isCheckbox=false;
			emCheckbox.each(function(){
				if($(this).is(':checked')){
					isCheckbox=true;
					return false;
				}
			})
			if(!isCheckbox){
				layer.tips(label.text() + '为必选项！', $(this), {
					tipsMore : true,
                    time: 10 * 500,
                    tips: 1
                });
				falg=false;
			}
		}else if($(this)[0].tagName=='SELECT'){
			if($(this).val()==''){
				layer.tips(label.text() + '为必选项！', $(this), {
					tipsMore : true,
                    time: 10 * 500,
                    tips: 1
                });
				falg=false;
			}
		}else{
			if($(this).val()==''){
				layer.tips(label.text() + '不能为空！', $(this), {
                    tipsMore : true,
                    time: 10 * 500,
                    tips: 1
                });
				falg=false;
			}
		}
	});
	if (falg) {
    	return true;
    } else {
        return false;
    }
}

function toJsonData(data){
	var values = {};
	for (var item in data) {
	   values[params[item].name] = params[item].value;
	}
	return JSON.stringify(values);
}

$(function(){
	if($('.changeBar').length>0){
		$('.changeBar').perfectScrollbar();
	}
	if($('.dataYmd').length>0){
	    $(".dataYmd").datetimepicker({language: 'zh-CN',minView: "month",format: 'yyyy-mm-dd',todayBtn: true,autoclose: true,pickerPosition: "bottom-left"});
	}
	if($('.dataYmdhi').length>0){
	    $(".dataYmdhi").datetimepicker({language: 'zh-CN',todayBtn: true,autoclose: true,pickerPosition: "bottom-left"});
	}
	
	if($('#table').length>0){
		$('#table').on('post-body.bs.table', function () {
			if($(':checkbox').length>0){
	            $(':checkbox').each(function(){
					if($(this).parent('.tableCheck').length<=0){
						$(this).wrap('<label class="tableCheck"></label>');
	            		$(this).parent('.tableCheck').append('<span></span>')
					}
				})
	        }
	    }).on('click-row.bs.table', function (e, row, ele,field) {
	    	if($(ele).find('input[type=checkbox]').length>0){
	        	$(ele).find('input[type=checkbox]').trigger('click');
	        }
	    })
   }
})
