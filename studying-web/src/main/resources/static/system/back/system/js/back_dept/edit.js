layui.use(['jquery','form','layer'],function () {
    let $ = layui.jquery,
        form = layui.form,
        layer = layui.layer,
        deptId = $('input[name="deptId"]').val(),
        index = parent.layer.getFrameIndex(window.name);

    const parentDeptId = $('input[name="superDeptId"]');

    $('input[name="superDeptName"]').on('click',function () {
        layer.open({
            title: '选择部门',
            type: 2,
            content: '/system/userDept',
            area:['400px','400px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                // 获得子弹出框的树
                let zTreeObj = layero.find('iframe')[0].contentWindow.layui.jquery.fn.zTree.getZTreeObj('companyTree');
                let selectDept = zTreeObj.getSelectedNodes()[0];
                parentDeptId.val(selectDept.departmentId);
                parentDeptId.attr('ancestors',selectDept.ancestors);
                form.val('deptForm',{
                    "superDeptName": selectDept.departmentName
                });
                layer.close(index);
            },
        });
    });

    form.verify({
        deptName:function (value,item) {
            let exist = true;
            let parentId = parentDeptId.val();
            checkNameIsExist();
            function checkNameIsExist(){
                $.ajax({
                    async: false,
                    method: 'get',
                    url: '/system/dept/checkDeptInSameParent?deptName='+value+'&parentId='+parentId+'&deptId='+deptId,
                    success:function (res) {
                        exist = res;
                    }
                });
            }
            if (exist){
                return "该部门名称在"+$('input[name="superDeptName"]').val()+"下重复";
            }
        },
        order:function (value,item) {
            if (isNaN(value)){
                return '排序需要是数字类型';
            }
        }
    });

    init();
    function init() {
        initFormValue();
    }
    
    function initFormValue() {
        $.get('/system/dept/init/'+deptId,function (data) {
            if (!data) return;
            form.val('deptForm',{
                dpetId: data.departmentId,
                superDeptName: data.parentName,
                superDeptId: data.parentId,
                deptName:data.departmentName,
                order: data.orderNum,
                leader: data.leader,
                phone: data.phone,
                email: data.email,
                deptStatus: data.status
            });
            parentDeptId.attr('ancestors',data.ancestors);
        });
    }

    form.on('submit(*)',function (obj) {
        let data = obj.field;
        let params = {
            departmentId: data.deptId,
            parentId: data.superDeptId,
            ancestors: parentDeptId.attr('ancestors')+','+data.superDeptId,
            departmentName: data.deptName,
            orderNum: data.order,
            leader: data.leader,
            phone: data.phone,
            email: data.email,
            status: data.deptStatus
        };
        $.ajax({
            url: '/system/dept/edit',
            type: 'post',
            data: JSON.stringify(params),
            headers:{
                'Content-Type': 'application/json'
            },
            success:function (data) {
                layer.msg(data.msg);
                if (data.code==0){
                    parent.layui.layer.close(index);
                    parent.layui.treeTable.reload('deptTable',{url:'/system/dept/find'});
                }
            }
        });
        return false;
    });
});