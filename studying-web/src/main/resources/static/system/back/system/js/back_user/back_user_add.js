layui.config({
    base: '/expandjs/'
}).extend({
    JqueryZtree: 'ztree/jquery-ztree'
}).use(['jquery','layer','JqueryZtree','form'],function () {
    let $ = layui.jquery,
        layer = layui.layer,
        form = layui.form,
    events = {
        close:function () {
            top.layui.pageOp.closeThisTab();
        }
    };
    form.verify({
        loginName:function (value,item) {
           if (value.length<2||value.length>20){
               return '登录名的长度应该不小于2且不大于20';
           }
            let exist = false;
            checkNameIsExist();
            function checkNameIsExist(){
               $.ajax({
                  async: false,
                  method: 'get',
                  url: '/system/user/checkRegisterName?loginName='+value,
                  success:function (res) {
                      exist = res.data;
                  }
               });
           }
           if (exist){
             return "该登录账号已被注册";
           }
        },
        loginPassword:function (value,item) {
            if (value.length<2||value.length>20){
                return "密码的长度要在2到20之间";
            }
        }
    });
    /**
     * 归属部门事件
     */
    $('input[name="dept"]').on('click',function () {
        layer.open({
            title: '选择部门',
            type: 2,
            content: '/system/userDept',
            area:['400px','500px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                // 获得子弹出框的树
                 let zTreeObj = layero.find('iframe')[0].contentWindow.layui.jquery.fn.zTree.getZTreeObj('companyTree');
                let selectDept = zTreeObj.getSelectedNodes()[0];
                $('input[name="dept"]').attr('dept-id',selectDept.departmentId);
                form.val('userForm',{
                    "dept": selectDept.departmentName
                });
                layer.close(index);
            },
        });
    });

    $('body').on('click','*[studying-event]',function () {
        let methodName = $(this).attr('studying-event');
        methodName && events[methodName].call(this);
    })
    /**
     * 提交
     */
    form.on('submit(*)',function (obj) {
        console.log(obj.field);
    });
});