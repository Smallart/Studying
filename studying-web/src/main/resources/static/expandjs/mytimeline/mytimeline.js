layui.define(['carousel'],function (exports) {
    let carousel = layui.carousel;
    const css_MyTimelineContainer = '.my-timeline-content',
        css_layuiTimeline = '.layui-timeline',
        animation_bgFadeIn= 'my-timeline-bg-fadein',
        animation_bgFadeOut = 'my-timeline-bg-fadeout',
        css_iconThis = 'layui-icon-this',
        css_pre = '.myTimeline-pre',
        css_next = '.myTimeline-next';
    // 声明一个时间轴的构造函数
    function MyTimeline(){
        this._options={
            selector:null,
            width: '800px',
            url: '/data/data.json',
            count: 7,
            timelineField: 'time',
            carouselField: 'todoItems'
        };
        this._config={
            requestData:null,
            timelineItemWidth: '110px',
            currentFirstIndex: 0,
            currentIndex: 0,
            currentEndIndex: 0,
            direction: true,
            step: 2
        }
    }
    MyTimeline.prototype = {};
    MyTimeline.prototype.constructor = MyTimeline;

    MyTimeline.prototype.render = function(options){
        if (options?.selector==null|undefined){
            console.error('请输入selector字段');
            return;
        }
        //赋值
        for(let item of Object.keys(options)){
            this._options[item] = options[item];
        }
        //创建框架
        try{
            this.initBasicFrame();
        }catch (e) {
            console.error(e);
        }
        this.initLeftStructure();
        this.registerClickEvent();
        this.initPreEvent();
        this.initNextEvent();
    };
    // 创建框架
    MyTimeline.prototype.initBasicFrame = function(){
       let container = document.querySelector(css_MyTimelineContainer);
       if (container==null|undefined){
           throw new Error('没有带.my-timeline-content的DOM声明');
       }
       container.style.width = this._options.width;
       let innerHtml =
           `<div class="myTimeline-pre">
                <i class="layui-icon">&#xe65a;</i>
            </div>
            <div class="myTimeline-next">
                <i class="layui-icon">&#xe65b;</i>
            </div>
            <ul class="layui-timeline"></ul>
            <div class="layui-carousel" id="${this._options.selector}">
                <div carousel-item>
                </div>
            </div>
            `;
       container.innerHTML = innerHtml;
    };

    //初始化具体内容
    MyTimeline.prototype.initLeftStructure = function(){
        let dataPromise = this.requestData();
        dataPromise.then((data)=>{
            this.createTimelineItem(data[this._options.timelineField]);
            this.initCarousel(data[this._options.carouselField]);
        })
    };

    //创建时间轴的内容
    MyTimeline.prototype.createTimelineItem = function(data){
        let ul = document.querySelector(css_layuiTimeline);
        let index = 0;
        this._options.count = Math.min(this._options.count,data.length);
        let itemWidth = ul.clientWidth / this._options.count;
        this._config.timelineItemWidth = itemWidth;
        this._options.count = Math.min(this._options.count,data.length);
        this._config.currentEndIndex = this._options.count-1;
        // 如果options没有设置step则计算出一个合适的step
        if (!this._options.hasOwnProperty('step')){
            this._config.step = this._options.count / 4 < 1 ? 1 : this._config.step;
        }
        let innerHtml = data.map(item=>{
            return `<li class="layui-timeline-item ${animation_bgFadeOut}" data-timeItem="${index++}">
                        <i class="layui-icon layui-timeline-axis">&#xe63f;</i>
                        <div class="layui-timeline-content layui-text my-timeline-text">
                            <h3 class="layui-timeline-title">${item}</h3>
                        </div>
                    </li>`;
        }).join('');
        document.documentElement.style.setProperty('--timeline-contain-width',itemWidth+'px');
        document.documentElement.style.setProperty('--timeline-item-left',(-itemWidth+20)/2 + 'px');
        ul.innerHTML = innerHtml;
        ul.children[0].classList.add(animation_bgFadeIn);
        ul.children[0].children[0].classList.add(css_iconThis);
    };
    //获取数据
    MyTimeline.prototype.requestData = function(){
        return fetch(this._options.url).then(response=>response.json()).then(data=> {
            this._config.requestData = data;
            return data;
        });
    };

    // 初始化展示轮播图
    MyTimeline.prototype.initCarousel = function(data){
        let carouselBody = document.querySelector('[carousel-item]');
        let index = 0;
        let innerHtml =data.map(todoes=>{
            let todoHtml = todoes.map(todo=>{
                return `<li class="${todo['finished']?'todo-finished':''}">${todo['content']}</li>`;
            }).join('');
            return `<div class="layui-timeline-content layui-text" data-todoItem="${index++}">
                        <ul>
                            ${todoHtml}
                        </ul>
                    </div>`;
        }).join('');
        carouselBody.innerHTML = innerHtml;
        carousel.render({
            elem: '#'+this._options.selector,
            anim: 'fade',
            arrow: 'none',
            indicator: 'none',
            autoplay: false,
            width: this._options.width,
            index: 0
        });
    };

    // 注册点击timeline点击事件
    MyTimeline.prototype.registerClickEvent = function(){
        let $this = this;
        let timeline = document.querySelector(css_layuiTimeline);
        timeline.addEventListener('click',e=>{
            let dom = e.target;
            if (!dom.matches('i')||$this.hasClass(dom,css_iconThis)){
                return;
            }
            let currentIndex = 0;
            let currentClickIndex = dom.parentNode.dataset.timeitem;
            let items = dom.parentNode.parentElement.children;
            for(let item of items){
                if($this.hasClass(item.children[0],css_iconThis)){
                    currentIndex = item.dataset.timeitem;
                }
            }
            // 移除当前显示被点击的item
            items[currentIndex].children[0].classList.remove(css_iconThis);
            // 增加动画
            if(currentIndex<currentClickIndex){
                while(currentIndex<currentClickIndex){
                    currentIndex++;
                    items[currentIndex].classList.remove(animation_bgFadeOut);
                    items[currentIndex].classList.add(animation_bgFadeIn);
                }
                $this._config.direction = true;
            }else{
                while(currentClickIndex<currentIndex){
                    items[currentIndex].classList.remove(animation_bgFadeIn);
                    items[currentIndex].classList.add(animation_bgFadeOut);
                    currentIndex--;
                }
                $this._config.direction = false;
            }
            dom.classList.add(css_iconThis);
            $this._config.currentIndex = currentClickIndex;
            $this.ChangeCarousel(currentIndex);
            $this.ChangeTimelinePosition();
        });
    };
    MyTimeline.prototype.hasClass = function(elem,clazzName){
        return elem.className.indexOf(clazzName)>-1;
    };

    //控制轮播图
    MyTimeline.prototype.ChangeCarousel = function(index){
        let carousels = document.querySelectorAll(`div[data-todoitem]`);
        for(carousel of carousels){
            carousel.classList.remove('layui-this');
            if(parseInt(carousel.dataset.todoitem)===index){
                carousel.classList.add('layui-this');
            }
        }
    };

    //timeline移动
    MyTimeline.prototype.ChangeTimelinePosition = function(){
        let lastVisitedIndex = this._config.currentIndex;
        let items = document.querySelectorAll('.layui-timeline-item');
        let count = 0;
        if (this._config.direction){
            let currentEndIndex = this._config.currentEndIndex;
            if (currentEndIndex-lastVisitedIndex>this._config.step||currentEndIndex==items.length-1){
                return;
            }
            count = items.length-1-currentEndIndex>this._config.step ? this._config.step : items.length - 1 - currentEndIndex;
            let distance = -count* this._config.timelineItemWidth;
            items.forEach(element=>{
                let left = element.style.left==''?0: parseInt(element.style.left);
                element.style.left = (left + distance) + 'px';
            });
            this._config.currentEndIndex+= count;
            this._config.currentFirstIndex += count;
        }else{
            let currentFirstIndex = this._config.currentFirstIndex;
            if(lastVisitedIndex-currentFirstIndex>this._config.step||(currentFirstIndex==0)){
                return;
            }
            count =currentFirstIndex > this._config.step ? this._config.step : currentFirstIndex;
            let distance = count * this._config.timelineItemWidth;
            items.forEach(element=>{
                let left = element.style.left==''?0: parseInt(element.style.left);
                element.style.left = (left + distance) + 'px';
            });
            this._config.currentFirstIndex -= count;
            this._config.currentEndIndex -= count;
        }
    };

    //添加左右点击事件
    MyTimeline.prototype.initPreEvent = function(){
        let preEvent = document.querySelector(css_pre);
        preEvent.addEventListener('click',()=>{
            let items = document.querySelectorAll('.layui-timeline-item');
            let count =Math.min(this._config.currentEndIndex-this._config.currentIndex,this._config.currentFirstIndex);
            let distance = count*this._config.timelineItemWidth;
            items.forEach(element=>{
                let left = element.style.left==''?0: parseInt(element.style.left);
                element.style.left = (left + distance) + 'px';
            });
            this._config.currentEndIndex -=count;
            this._config.currentFirstIndex -=count;
        });
    };
    MyTimeline.prototype.initNextEvent = function(){
        let nextEvent = document.querySelector(css_next);
        nextEvent.addEventListener('click',()=>{
            let items = document.querySelectorAll('.layui-timeline-item');
            let count = Math.min(this._config.currentIndex-this._config.currentFirstIndex,items.length-1-this._config.currentEndIndex);
            let distance = -count*this._config.timelineItemWidth;
            items.forEach(element=>{
                let left = element.style.left==''?0: parseInt(element.style.left);
                element.style.left = (left + distance) + 'px';
            });
            this._config.currentEndIndex +=count;
            this._config.currentFirstIndex +=count;
        });
    };
    exports('myTimeline',new MyTimeline());
});