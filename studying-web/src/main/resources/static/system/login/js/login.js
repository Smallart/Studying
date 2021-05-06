layui.use(['form','jquery'],function () {
   var form = layui.form,
       $ = layui.jquery;
   changeCaptcha();
   /**
    * 初始化点击事件
    */
   function changeCaptcha() {
      let captchaImg = $('#captcha');
      captchaImg.click(function () {
         let url = '/captcha?time='+(new Date()).getMilliseconds();
         captchaImg.attr('src',url);
      });
   }

   /**
    * 登录
    */
   form.on('submit(*)',function (data) {
      $.post('/sysLogin/login',{
         userName:data.field.userName,
         password:data.field.password,
         rememberMe:data.field.rememberMe?? false,
         captcha:data.field.captcha
      }).then(function (data) {
         if (data.code==0){
            layer.msg(data.msg);
         }else{
            layer.alert("<span style='color:red'>登录失败，失败原因："+data.msg+"</span>",{icon:5});
            $('#captcha').click();
         }
      }).fail(function (data) {
         console.log(data);
      });
   });

});