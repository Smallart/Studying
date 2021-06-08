layui.config({
    base: '/expandjs/'
}).extend({
    iconHhysFa: 'iconHhys/iconHhysFa'
}).use(['iconHhysFa','form'],function () {
    let iconHhysFa = layui.iconHhysFa,
        form = layui.form;
    iconHhysFa.render({
        // 选择器，推荐使用input
        elem: '#iconHhysFa',
        // 数据类型：fontClass/awesome，推荐使用fontClass
        type: 'fontClass',
        // 是否开启搜索：true/false，默认true
        search: true,
        // 是否开启分页：true/false，默认true
        page: true,
        // 每页显示数量，默认12
        limit: 12,
        cellWidth:'80px',
        // 点击回调
        click: function(data) {
            console.log(data);
        },
        // 渲染成功后的回调
        success: function(d) {
            console.log(d);
        }
    });
});