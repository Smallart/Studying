layui.config({
    base: '/expandjs/'
}).extend({
    treeTable: 'treeTable/treeTable'
}).use(['jquery','treeTable','form'],function () {
    let $ = layui.jquery,
        treeTable = layui.treeTable,
        form = layui.form,
        tag = true,
        events={
            add:function () {
                layer.open({
                    title: "添加部门",
                    type: 2,
                    content: '/system/dept/add',
                    area:['600px','520px'],
                    btn:['确定','关闭'],
                    yes:function (index,layero) {
                        layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
                    },
                    btn2: function () {

                    }
                });
            },
            treeOp:function () {
                if(tag){
                    table.expandAll();
                }else{
                    table.foldAll();
                }
                tag = !tag;
            },
            edit:function () {
                let data = table.checkStatus();
                if (data.length!=1){
                    layer.msg("请选择某一个菜单进行操作");
                    return;
                }
                layer.open({
                    title: "修改部门",
                    type: 2,
                    content: '/system/dept/edit?deptId='+data[0].departmentId,
                    area:['600px','520px'],
                    btn:['确定','关闭'],
                    yes:function (index,layero) {
                        layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
                    },
                    btn2: function () {

                    }
                });
            }
        };
    const editBtn = $('#edit');
    let table = treeTable.render({
        elem: '#deptTable',
        skin: 'line',
        url: '/system/dept/find',
        tree:{
            iconIndex: 1,
            isPidData: true,
            idName: 'departmentId',
            pidName: 'parentId'
        },
        cols:[[
            {type:'radio'},
            {field:'departmentName',title:'部门名称',align:'center',minWidth:140},
            {field:'orderNum',title:'排序',align:'center',width:25},
            {title:'状态',toolbar:'#deptStatus',align:'center',width:25},
            {title:'创建时间',align:'center',templet:"<div>{{layui.util.toDateString(d.createTime,'yyyy-MM-dd HH:mm:ss')}}</div>"},
            {title:'操作',align:'center',toolbar:'#operatorButtons',minWidth:120}
        ]]
    });
    // 为单选框添加事件
    treeTable.on('radio(deptTable)',function () {
        if(this.checked){
            editBtn.removeClass('layui-btn-disabled');
        }else{
            editBtn.addClass('layui-btn-disabled');
        }
    });

    form.on('submit(*)',function(data){
        let params={
            deptName: data.field.deptName,
            deptStatus: data.field.deptStatus
        };
        reloadTable(params);
        return false;
    });
    // 重新加载Table
    function reloadTable(params){
        table.reload({
            url: '/system/dept/find',
            where: params
        });
    }

    $('body').on('click','*[studying-event]',function () {
         let eventName = $(this).attr('studying-event');
         eventName&&events[eventName].call(this);
    });
    // 表格tool
    treeTable.on('tool(deptTable)',function (obj) {
        switch (obj.event) {
            case 'add':
                add(obj.data);
                break;
            case 'edit':
                edit(obj.data);
                break;
            case 'del':
                del(obj.data);
                break;
        }
    });

    // 修改
    function edit(data) {
        layer.open({
            title: "修改部门",
            type: 2,
            content: '/system/dept/edit?deptId='+data.departmentId,
            area:['600px','520px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
            },
            btn2: function () {

            }
        });
    }

    // 新增
    function add(data) {
        layer.open({
            title: "添加部门",
            type: 2,
            content: '/system/dept/add?deptId='+data.departmentId,
            area:['600px','520px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
            },
            btn2: function () {

            }
        });
    }

    // 删除
    function del(data) {
        layer.confirm('是否删除所选择部门？', {icon: 3, title:'提示',skin:'warn-new-skin'}, function(index){
            $.get('/system/dept/del?deptId='+data.departmentId,function (resp) {
                if (resp.code!=0){
                    layer.msg(resp.msg,{icon: 5});
                }else{
                    layer.msg(resp.msg,{icon: 1});
                }
                reloadTable(null);
            });
        });
    }
});