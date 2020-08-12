var home = new Vue({
	el : '#home',
	data:{
		users:null,
		user:{},
		mode:"BROWSE",
		title: "Home",
		login:false,
		register:false
	},
	mounted () {
    },
	methods:{
		login : function(){
			$("#myModal").modal('show');
		},
		register : function(){
			$("#myModal").modal('show');
		},
		register : function(user){
			axios
	          .post('/Project/rest/users/register', user)
	          .then(response => (this.user = response.data))
		}
	}
});