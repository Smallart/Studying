layui.config({
    base: '/expandjs/'
}).extend({
    xmSelect: 'xmSelect/xm-select',
    studying: 'studying/studying'
}).use(['jquery','layer','studying','xmSelect'],function () {
    let $ = layui.jquery,
        layer = layui.layer,
        xmSelect = layui.xmSelect,
        postSelect=xmSelect.render({
            el:'#post'
        }),
        studying = layui.studying,
        studyingConfig = {
            form:{
                enable:true,
                events:{
                    submit:{
                        filter: '*',
                        event: submitEvent
                    }
                },
                verify:{
                    enable:false,
                    rule:{
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
                    }
                }
            },
            globalEvents:{
                enable: true,
                close: closeEvent
            }
        },
        form = studying.form();


    studying.render(studyingConfig);

    initSelect();
    initRoleCheckedBox();

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

    /**
     * 初始化Select
     */
    function initSelect(){
        $.get('/system/post/markingPostById',function (resp) {
            let data = resp.map(item=>{
                let obj = {
                    name: item.postName,
                    value: item.postId,
                    selected: item.flag
                };
                if (obj.status===0){
                    obj.disabled = true;
                    delete obj.selected;
                }
                return obj;
            });
            postSelect.update({
                data:data
            })
        })
    }

    /**
     * 初始化角色checkbox
     */
    function initRoleCheckedBox() {
        $.get('/system/role/selectRolesByUserId/'+-1,function (resp) {
            let checkbox = $('#role');
            let innerHtml =resp.map(item=>`<input type="checkbox" name="role" title="${item.roleName}" value="${item.roleId}" lay-skin="primary" ${item.flag?'checked':''}>`).join('');
            checkbox.html(innerHtml);
            form.render('checkbox');
        });
    }

    /**
     * 提交
     * @param obj
     */
    function submitEvent(obj){
        let params = obj.field;
        params.deptId = parseInt($('input[name="dept"]').attr('dept-id'));
        params.postIds = postSelect.getValue().map(item=>item.value);
        params.roles = Array.from(document.querySelectorAll('input[name="role"]:checked')).map(item=>parseInt(item.getAttribute('value')));
        params.status = $('input[name="status"]').is(':checked')?0:1;
        delete params.dept;
        delete params.select;
        delete params.role;
        $.ajax({
            url: '/system/user/save',
            type: 'post',
            data: JSON.stringify(params),
            headers:{
                'Content-Type': 'application/json'
            },
            success:function (data) {
                layer.msg(data.msg);
            }
        });
    }

    /**
     * close关闭事件
     */
    function closeEvent() {
        top.layui.pageOp.closeThisTab();
    }
});