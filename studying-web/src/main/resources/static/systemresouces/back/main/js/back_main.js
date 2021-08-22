layui.config({
    base: '/expandjs/'
}).extend({
    myTimeline:'mytimeline/mytimeline'
}).use(['element','myTimeline'],function () {
    var element = layui.element,
        myTimeline = layui.myTimeline;
    myTimeline.render({
        selector:'test1',
        width: '1000px'
    });
});