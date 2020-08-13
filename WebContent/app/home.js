var home = new Vue({
	el : '#home',
	data:{
		users:null,
		newUser:{},
		mode:"BROWSE",
		title: "Home",
		searchFieldUsername:"",
		searchedUsers:null
	},
	mounted(){
	},
	methods:{
		getAllUsers: function(){
    		axios
    		.get("/Project/rest/users/getAllUsers")
    		.then(response => (this.users = response.data))
    	}
	}
});

var register = new Vue({
	el:'#registerModal',
	data:{
		newUser:{}
	},
	mounted () {
		this.newUser.role = "GUEST";
    },
	methods:{
		register : function(user){
			axios
	          .post("/Project/rest/users/register", user)
	          .then(response => (this.user = response.data))
		}
	}
});