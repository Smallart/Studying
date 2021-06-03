layui.config({
    base: '/expandjs/'
}).extend({
    treeTable: 'treeTable/treeTable'
}).use(['form','treeTable'],function () {
    let form = layui.from,
        treeTable = layui.treeTable;

    let table = treeTable.render({
        elem: '#menuTable',
        skin: 'line',
        url: '/system/menusTable',
        tree:{
            iconIndex: 1,
            isPidData: true,
            idName: 'menuId',
            pidName: 'parentId',
            getIcon: function (d) {
                return `<i class="${d.icon}"></i>`;
            }
        },
        cols:[[
            {type:'radio'},
            {field:'menuName',title:'菜单名称',align:'center',minWidth:140},
            {field:'orderNum',title:'排序',align:'center',width:25},
            {field:'url',title:'请求地址',align:'center'},
            {title:'类型',toolbar:'#menuType',align:'center',width:25},
            {toolbar:'#visible',title:'可见',align:'center',width:25},
            {field:'perms',title:'权限标识',align:'center'},
            {title:'操作',align:'center',toolbar:'#operatorButtons',minWidth:120}
        ]]
    });
});