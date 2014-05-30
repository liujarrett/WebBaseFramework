/*
 * 中科方德table拖拽控件(需依赖于JQUERY,引用此js前需先添加JQUERY引用)
 * 康齐 (可使用于两个table间来回拖拽，或者用于排序)
 * 2011-4-12
 * 		
 */
var mPressed = false; //鼠标按下
var mMoved = false;  //鼠标移动
var dragSource = false; //拖拽节点数据
/************************************拖拽排序控件 开始***********************************/
$.fn.nfpDrag = function (opt) {
    return this.each(function () {
        var TABLE = this; 	//本拖拽控件
        var interv = null;  //滚动条循环事件
        TABLE.option = {
            /***********可配置内容(可扩展)************/
            dragStyle: true, //拖拽原始位置是否启用样式标示
            /**********事件*************/
            afterMove: false //拖拽完后执行的方法
            /*********可扩展自定义属性或方法********/
        };
        TABLE.option = $.extend(TABLE.option, opt);

        //设置拖拽事件
        TABLE.setDragEvent = function (tdOrTh) {
            tdOrTh.bind('selectstart', function () {
                return false;
            }).click(function () {
                return false;
            }).dblclick(function () {
                //双击事件
                mPressed = false;
                return false;
            }).mousedown(function (event) {
                if (event.which != 1)
                    return false;
                if ($("#drag_container").size() == 1) {
                    TABLE.moveTR($(this).parent());
                    TABLE.eventDestroy();
                    return false;
                }
                //鼠标按下事件,执行拖拽
                mPressed = true;
                var TR = $(this).parent();
                //保存拖拽前节点,跳出浮动拖拽提示层
                cloneTr = TR.clone();
                $('body').append('<div id="drag_container"><table border="0" cellspacing="5"></table></div>');
                $('#drag_container').hide().css({ opacity: '0.8' });
                $('#drag_container >table').append(cloneTr);
                $(document).bind("mousemove", { TR: TR }, TABLE.dragStart).bind("mouseup", TABLE.dragEnd);
                return false;
            }).mouseup(function () {
                //鼠标松开事件,完成拖拽
                if (mPressed && mMoved && dragSource) {
                    TABLE.moveTR($(this).parent());
                }
                TABLE.eventDestroy();
            });
        };
        TABLE.clearScroll = function () {
            window.clearInterval(interv);
            interv = null;
        }

        //开始拖拽
        TABLE.dragStart = function (event) {
            var TR = $(event.data.TR);
            if (mPressed) {
                if (event.pageY > $(TABLE).parent().height() + $(TABLE).offset().top + $(TABLE).parent().scrollTop()) {
                    if (interv == null)
                    //向下滚动
                        interv = window.setInterval(function () { $(TABLE).parent().scrollTop($(TABLE).parent().scrollTop() + 3); }, 10);
                } else if (event.pageY < $(TABLE).offset().top + $(TABLE).parent().scrollTop()) {
                    if (interv == null)
                    //向下滚动
                        interv = window.setInterval(function () { $(TABLE).parent().scrollTop($(TABLE).parent().scrollTop() - 3); }, 10);
                } else {
                    TABLE.clearScroll();
                }
                mMoved = true;
                if ($('#drag_container:not(:visible)')) {
                    //保存拖拽参数
                    $('#drag_container').show();
                    if (TABLE.option.dragStyle) {
                        TR.prev().find("[class*='allowDrag']").addClass("startDrag");
                    }
                    TR.prev().attr("tempTag", "true");
                    dragSource = TR;
                }
                //拖拽浮动提示层
                $('#drag_container').css({ position: 'absolute', "left": (event.pageX + 5), "top": (event.pageY + 15) });
                if (TR.is(':visible')) TR.hide();
                if ($(event.target).attr("class").indexOf("allowDrag") != -1) {
                    if ($(event.target).attr("class").indexOf("tdDrag") == -1) {
                        $("[class*='tdDrag']").removeClass("tdDrag");
                        $(event.target).parent().find("[class*='allowDrag']").addClass("tdDrag");
                    }
                } else {
                    $("[class*='tdDrag']").removeClass("tdDrag");
                }
                return false;
            }
            return true;
        };
        //拖拽完成
        TABLE.dragEnd = function () {
            //停止拖拽事件,清理临时参数
            TABLE.eventDestroy();
        };

        //停止拖拽事件,清理临时参数
        TABLE.eventDestroy = function () {
            TABLE.clearScroll();
            $(document).unbind('mousemove', TABLE.dragStart).unbind('mouseup').unbind('mousedown');
            $('#drag_container').remove();
            $("tr[tempTag]").removeAttr("tempTag");
            if (dragSource) {
                dragSource.show();
            }
            $("[class*='startDrag']").removeClass("startDrag");
            $("[class*='tdDrag']").removeClass("tdDrag");
            dragSource = mPressed = mMoved = false;
        };
        //将节点移动置节点中
        TABLE.moveTR = function (tr) {
            //拖拽回原位置,不做任何修改
            if ($(tr).attr("tempTag") == 'true') {
                return true;
            }
            tr.after($("#drag_container tr").parent().html());
            TABLE.setDragEvent(tr.next().children());
            //拖拽后事件
            if (typeof (TABLE.option.afterMove) == 'function') {
                TABLE.option.afterMove(tr.next(), tr);
            }
            $(dragSource).remove();
            dragSource = false;
        };

        //拖拽控件初始化
        TABLE.init = function () {
            //添加样式
            $(TABLE).find("td,th").addClass("allowDrag").each(function () {
                if ($(this).parent().prev().attr("class") != null) {
                    TABLE.setDragEvent($(this));
                } else {
                    $(this).mousedown(function () {
                        return false;
                    }).mouseup(function () {
                        //鼠标松开事件,完成拖拽
                        if (mPressed && mMoved && dragSource) {
                            TABLE.moveTR($(this).parent());
                        }
                        TABLE.eventDestroy();
                    });
                }
            });
        };
        TABLE.init();
    });
}
