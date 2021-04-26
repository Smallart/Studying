layui.use(['form','jquery'],function () {
   var form = layui.form,
       $ = layui.jquery;
   changeCaptcha();
   /**
    * 初始化点击事件
    */
   function changeCaptcha() {
      var captchaImg = $('#captcha');
      captchaImg.click(function () {
         let url = '/captcha?time='+(new Date()).getMilliseconds();
         captchaImg.attr('src',url);
      });
   }

});