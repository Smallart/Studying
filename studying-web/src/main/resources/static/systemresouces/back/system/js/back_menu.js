layui.config({
    base: '/expandjs/'
}).extend({
    treeTable: 'treeTable/treeTable'
}).use(['jquery','form','treeTable','layer'],function () {
    let form = layui.form,
        treeTable = layui.treeTable,
        $ = layui.jquery,
        layer = layui.layer,
        tag = true,
        events={
            add:function () {
                layer.open({
                    title: "添加菜单",
                    type: 2,
                    content: '/system/menu/add',
                    area:['600px','600px'],
                    btn:['确定','关闭'],
                    yes:function (index,layero) {
                        layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
                        layer.close(index);
                        reloadTable(null);
                    },
                    btn2: function () {

                    }
                });
            },
            reset:function () {
                reloadTable(null);
            },
            treeTableOp:function () {
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
                    title: "修改菜单",
                    type: 2,
                    content: '/system/menu/edit?menuId='+data[0].menuId,
                    area:['600px','600px'],
                    btn:['确定','关闭'],
                    yes:function (index,layero) {
                        layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
                        layer.close(index);
                        reloadTable(null);
                    },
                    btn2: function () {

                    }
                });
            }
        };
    const editBtn = $('#edit');

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

    $('body').on('click','*[studying-event]',function (event) {
        let eventsName = $(this).attr('studying-event');
        eventsName&&events[eventsName].call(this);
    });

    treeTable.on('radio(menuTable)',function () {
        if(this.checked){
            editBtn.removeClass('layui-btn-disabled');
        }else{
            editBtn.addClass('layui-btn-disabled');
        }
    });

    // 监听表单提交事件
    form.on('submit(*)',function(data){
        let params={
            menuName: data.field.menuName,
            visible: data.field.visible
        };
        reloadTable(params);
        return false;
    });

    function reloadTable(params){
        table.reload({
            url: '/system/menusTable',
            where: params
        });
    }

    //事件tool监听
    treeTable.on('tool(menuTable)',function (obj) {
        switch(obj.event){
            case "add":
                add(obj.data);
                break;
            case "edit":
                edit(obj.data);
                break;
            case "delete":
                del(obj.data);
                break;
        }
        reloadTable(null);
    });

    function add(data) {
        layer.open({
            title: "添加子菜单",
            type: 2,
            content: '/system/menu/add?menuId='+data.menuId,
            area:['600px','600px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
                layer.close(index);
                reloadTable(null);
            },
            btn2: function () {

            }
        });
    }

    function edit(data) {
        layer.open({
            title: "修改菜单",
            type: 2,
            content: '/system/menu/edit?menuId='+data.menuId,
            area:['600px','600px'],
            btn:['确定','关闭'],
            yes:function (index,layero) {
                layero.find('iframe')[0].contentWindow.document.getElementById("search").click();
                layer.close(index);
                reloadTable(null);
            },
            btn2: function () {

            }
        });
    }

    function del(data) {
        layer.confirm('是否删除所选择菜单？', {icon: 3, title:'提示',skin:'warn-new-skin'}, function(index){
            $.get('/system/menu/del/'+data.menuId,function (resp) {
                if (resp.code!=0){
                    layer.msg(resp.msg,{icon: 5});
                }else{
                    layer.msg(resp.msg,{icon: 1});
                }
            });
        });
    }
});