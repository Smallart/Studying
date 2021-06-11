layui.config({
    base: '/expandjs/'
}).extend({
    JqueryZtree: 'ztree/jquery-ztree',
    xmSelect: 'xmSelect/xm-select'
}).use(['jquery','form','xmSelect','layer'],function () {
    let $ = layui.jquery,
        form = layui.form,
        xmSelect= layui.xmSelect,
        layer = layui.layer,
        postSelect=xmSelect.render({
            el:'#post'
        }),userId = $('input[name="userId"]').val();

    init();
    function  init() {
        initSelect();
        initRoleCheckedBox();
    }

    function initSelect(){
        $.get('/system/post/markingPostById?userId='+userId,function (resp) {
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
        });
    }
    
    function initRoleCheckedBox() {
        $.get('/system/role/selectRolesByUserId/'+userId,function (resp) {
           let checkbox = $('#role');
           let innerHtml =resp.map(item=>`<input type="checkbox" name="role" title="${item.roleName}" value="${item.roleId}" lay-skin="primary" ${item.flag?'checked':''} ${item.status==1?'disabled':''}>`).join('');
           checkbox.html(innerHtml);
           form.render('checkbox');
        });
    }

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

    form.on('submit(*)',function (obj) {
        let params = obj.field;
        params.deptId = parseInt($('input[name="dept"]').attr('dept-id'));
        params.postIds = postSelect.getValue().map(item=>item.value);
        params.roles = Array.from(document.querySelectorAll('input[name="role"]:checked')).map(item=>parseInt(item.getAttribute('value')));
        params.status = $('input[name="status"]').is(':checked')?0:1;
        delete params.dept;
        delete params.select;
        delete params.role;
        $.ajax({
            url: '/system/user/edit',
            type: 'post',
            data: JSON.stringify(params),
            headers:{
                'Content-Type': 'application/json'
            },
            success:function (data) {
                layer.msg(data.msg);
            }
        });
    })
});