var home = new Vue({
	el : '#home',
	data:{
		users:null,
		user:{},
		mode:"BROWSE",
		title: "Home"
	},
	mounted () {
		
    },
	methods:{
		login : function(){
			this.mode = "LOGIN";
		}
	}
});