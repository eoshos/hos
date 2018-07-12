/**
 * Created by TF on 2018/1/15.
 */
function creatImg(arrPic,img_index,len){
	 //给body添加弹出层的html
    $("body").append("<div class=\"mask-layer\">" +
        "   <div class=\"mask-layer-black\"></div>" +
        "   <div class=\"mask-layer-container\">" +
        "       <div class=\"mask-layer-container-operate\">" +
        "           <button class=\"mask-prev btn-default-styles\" style=\"float: left\">上一张</button>" +
        "           <button class=\"mask-out btn-default-styles\">放大</button>" +
        "           <button class=\"mask-in btn-default-styles\">缩小</button>" +
        "           <button class=\"mask-clockwise btn-default-styles\">顺旋转</button>" +
        "           <button class=\"mask-counterclockwise btn-default-styles\">逆旋转</button>" +
        "           <button class=\"mask-close btn-default-styles\">关闭</button>" +
        "           <button class=\"mask-next btn-default-styles\" style=\"float: right\">下一张</button>" +
        "       </div>" +
        "       <div class=\"mask-layer-imgbox auto-img-center\"></div>" +
        "		<div class=\"auto-txt-center\"></div>" +
        "   </div>" +
        "</div>"
   );
    var num = img_index;

    function showImg() {
        $(".mask-layer-imgbox").append("<p><img src=\"\" alt=\"\"></p>");
        $(".mask-layer-imgbox img").prop("src", arrPic[num].src); //给弹出框的Img赋值
		$(".auto-txt-center").html(arrPic[num].alt);
        //图片居中显示
        var box_width = $(".auto-img-center").width(); //图片盒子宽度
        var box_height = $(".auto-img-center").height();//图片高度高度
        var initial_width=arrPic[num].width;
        var initial_height=arrPic[num].height;
        console.log(initial_width+'----'+initial_height);
        if (initial_width > initial_height) {
            $(".auto-img-center img").css("width", box_width);
            var last_imgHeight = initial_height*(box_width/initial_width);
            $(".auto-img-center img").css("margin-top", -(last_imgHeight - box_height) / 2);
        } else {
            $(".auto-img-center img").css("height", box_height);
            var last_imgWidth = initial_width*(box_height/initial_height);
            $(".auto-img-center img").css("margin-left", -(last_imgWidth - box_width) / 2);
        }

        //图片拖拽
        var $div_img = $(".mask-layer-imgbox p");
        //绑定鼠标左键按住事件
        $div_img.bind("mousedown", function (event) {
            event.preventDefault && event.preventDefault(); //去掉图片拖动响应
            //获取需要拖动节点的坐标
            var offset_x = $(this)[0].offsetLeft;//x坐标
            var offset_y = $(this)[0].offsetTop;//y坐标
            //获取当前鼠标的坐标
            var mouse_x = event.pageX;
            var mouse_y = event.pageY;
            //绑定拖动事件
            //由于拖动时，可能鼠标会移出元素，所以应该使用全局（document）元素
            $(".mask-layer-imgbox").bind("mousemove", function (ev) {
                // 计算鼠标移动了的位置
                var _x = ev.pageX - mouse_x;
                var _y = ev.pageY - mouse_y;
                //设置移动后的元素坐标
                var now_x = (offset_x + _x ) + "px";
                var now_y = (offset_y + _y ) + "px";
                //改变目标元素的位置
                $div_img.css({
                    top: now_y,
                    left: now_x
                });
            });
        });
        //当鼠标左键松开，接触事件绑定
        $(".mask-layer-imgbox").bind("mouseup", function () {
            $(this).unbind("mousemove");
        });

        //缩放 放大
        var zoom_n = 1;
        $(".mask-out").click(function () {
            zoom_n += 0.1;
            $(".mask-layer-imgbox img").css({
                "transform": "scale(" + zoom_n + ")",
                "-moz-transform": "scale(" + zoom_n + ")",
                "-ms-transform": "scale(" + zoom_n + ")",
                "-o-transform": "scale(" + zoom_n + ")",
                "-webkit-transform": "scale(" + zoom_n + ")"
            });
        });
        //缩放 缩小
        $(".mask-in").click(function () {
            zoom_n -= 0.1;
            zoom_n = zoom_n <= 0.1 ? 0.1 : zoom_n;
            $(".mask-layer-imgbox img").css({
                "transform": "scale(" + zoom_n + ")",
                "-moz-transform": "scale(" + zoom_n + ")",
                "-ms-transform": "scale(" + zoom_n + ")",
                "-o-transform": "scale(" + zoom_n + ")",
                "-webkit-transform": "scale(" + zoom_n + ")"
            });
        });
        //旋转
        var spin_n = 0;
        $(".mask-clockwise").click(function () {
            spin_n += 90;
            $(".mask-layer-imgbox img").parent("p").css({
                "transform":"rotate("+ spin_n +"deg)",
                "-moz-transform":"rotate("+ spin_n +"deg)",
                "-ms-transform":"rotate("+ spin_n +"deg)",
                "-o-transform":"rotate("+ spin_n +"deg)",
                "-webkit-transform":"rotate("+ spin_n +"deg)"
            });
        });
        $(".mask-counterclockwise").click(function () {
            spin_n -= 90;
            $(".mask-layer-imgbox img").parent("p").css({
                "transform":"rotate("+ spin_n +"deg)",
                "-moz-transform":"rotate("+ spin_n +"deg)",
                "-ms-transform":"rotate("+ spin_n +"deg)",
                "-o-transform":"rotate("+ spin_n +"deg)",
                "-webkit-transform":"rotate("+ spin_n +"deg)"
            });
        });
        //关闭
        $(".mask-close").click(function () {
            $(".mask-layer").remove();
        });
        $(".mask-layer-black").click(function () {
            $(".mask-layer").remove();
        });
    }
    showImg();

    //下一张
    $(".mask-next").on("click", function () {
        $(".mask-layer-imgbox p img").remove();
        num++;
        if (num == len) {
            num = 0;
        }
        showImg();
    });
    //上一张
    $(".mask-prev").on("click", function () {
        $(".mask-layer-imgbox p img").remove();
        num--;
        if (num == -1) {
            num = len - 1;
        }
        showImg();
    });
}
