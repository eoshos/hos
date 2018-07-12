;
!function(win){
	"use strict";
	var doc = document,
		query = 'querySelectorAll',
		claname = 'getElementsByClassName',
		S = function(s) {
			return doc[query](s);
		};
	//默认配置
	var config = {
		type: 0,
		shade: true,
		close: true,
		direction:'left',
		rollType:true,
		rTime:2,
		bubble:true,
		switch:true
	};
	var ready = {
		extend: function(obj) {
			var newobj = JSON.parse(JSON.stringify(config));
			for(var i in obj) {
				newobj[i] = obj[i];
			}
			return newobj;
		},
		timer: {},
		end: {}
	};
	//点触事件
	ready.touch = function(elem, fn) {
		elem.addEventListener('click', function(e) {
			fn.call(this, e);
		}, false);
	};
	
	var index = 0,
	classs = ['ckPopup'],
	Layer = function(options) {
		var that = this;
		that.config = ready.extend(options);
		that.view();
	};
	Layer.prototype.view = function() {
		var that = this,
			config = that.config,
			layerbox = doc.createElement('div');
		that.id = layerbox.id = classs[0] + index;
		layerbox.setAttribute('class', classs[0] + ' ' + classs[0] + (config.type || 0)+ ' ' +(config.shade ?'ckShare':''));
		layerbox.setAttribute('index', index);
	
		//标题区域
		var title = (function() {
			return config.title ?'<h2 class="title">' + config.title + '</h2>' :'';
		}());
		
		//图片区域
		var img = (function() {
			return config.img ?'<div class="img"><img src="'+config.img+'"/></div>' :'';
		}());
		
		//内容区域
		var content = (function() {
			return config.content ?'<div class="con">'+config.content+'</div>' :'';
		}());
		
		//按钮区域
		var button = (function() {
			typeof config.btn === 'string' && (config.btn = [config.btn]);
			var btns = (config.btn || []).length,
				btndom;
			if(btns === 0 || !config.btn) {
				return '';
			}
			btndom = '<span yes type="1">' + config.btn[0] + '</span>'
			if(btns === 2) {
				btndom = '<span no type="0">' + config.btn[1] + '</span>' + btndom;
			}
			return '<div class="ckBtnBox">' + btndom + '</div>';
		}());
		if(config.time){
			var styleName=config.styleName?config.styleName:'';
			layerbox.innerHTML = '<div class="timeMsg '+styleName+'">'+content+'</div>';
		}else{
			layerbox.innerHTML = '<div class="popup">'+img+title+content+button+'</div>';
		}
		document.body.appendChild(layerbox);
			var elem = that.elem = S('#' + that.id)[0];
			config.success && config.success(elem);
	
			that.index = index++;
			that.action(config, elem);
	}
	Layer.prototype.action = function(config, elem) {
		var that = this;

		//自动关闭
		if(config.time) {
			ready.timer[that.index] = setTimeout(function() {
				ck.close(that.index);
			}, config.time * 1000);
		}
		//确认取消
		var btn = function() {
			var type = this.getAttribute('type');
			if(type == 0) {
				config.no ? config.no(that.index) : ck.close(that.index);
			} else {
				config.yes ? config.yes(that.index) : ck.close(that.index);
			}
		};
		if(config.btn) {
			var btns = elem[claname]('ckBtnBox')[0].children,
				btnlen = btns.length;
			for(var ii = 0; ii < btnlen; ii++) {
				ready.touch(btns[ii], btn);
			}
		}
	};
	
	win.ck = {
		v: '2.0',
		index: index,

		//核心方法
		msg: function(options) {
			var o = new Layer(options || {});
			return o.index;
		},
		close: function(index) {
			var ibox = S('#' + classs[0] + index)[0];
			if(!ibox) return;
			ibox.innerHTML = '';
			doc.body.removeChild(ibox);
			clearTimeout(ready.timer[index]);
			delete ready.timer[index];
			typeof ready.end[index] === 'function' && ready.end[index]();
			delete ready.end[index];
		},

		//关闭所有layer层
		closeAll: function() {
			var boxs = doc[claname](classs[0]);
			for(var i = 0, len = boxs.length; i < len; i++) {
				ck.close((boxs[0].getAttribute('index') | 0));
			}
		}
	};
		
}(window)
