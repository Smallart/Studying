layui.config({
    base: '/expandjs/'
}).extend({
    treeTable: 'treeTable/treeTable'
}).use(['jquery','treeTable','form'],function () {
    let $ = layui.jquery,
        treeTable = layui.treeTable,
        events={
            add:function () {
                layer.open({
                    title: "添加部门",
                    type: 2,
                    content: '/system/dept/add',
                    area:['600px','520px'],
                    btn:['确定','关闭'],
                    yes:function (index,layero) {

                    },
                    btn2: function () {

                    }
                });
            }
        };
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
    $('body').on('click','*[studying-event]',function () {
         let eventName = $(this).attr('studying-event');
         eventName&&events[eventName].call(this);
    });
});