layui.use(['jquery','form','layer'],function () {
    let $ = layui.jquery,
        form = layui.form,
        layer = layui.layer;
    $('input[name="superDept"]').on('click',function () {
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
                $('input[name="superDept"]').attr('dept-id',selectDept.departmentId);
                form.val('deptForm',{
                    "superDept": selectDept.departmentName
                });
                layer.close(index);
            },
        });
    });
});