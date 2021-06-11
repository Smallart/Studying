layui.use(['jquery','form'],function () {
    let $ = layui.jquery,
        form = layui.form;

    $('.pwdIcon').on('click',function () {
        let icon = $(this);
        icon.toggleClass('layui-icon-password');
        icon.toggleClass('layui-icon-key');
        let pwdInput = $('input[name="password"]');
        if (icon.hasClass('layui-icon-password')){
            pwdInput.attr('type','password');
        }else{
            pwdInput.attr('type','text');
        }
    });
    
    form.on('submit(*)',function (obj) {
        $.ajax({
            url: '/system/user/resetPwd',
            type: 'post',
            data: JSON.stringify(obj.field),
            headers:{
                'Content-Type': 'application/json'
            },
            success:function (data) {
                layer.msg(data.msg);
            }
        });
        return false;
    })
});