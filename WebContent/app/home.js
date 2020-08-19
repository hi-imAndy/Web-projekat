var home = new Vue({
	el : '#home',
	data:{
		users:null,
		newUser:{},
		mode:"BROWSE",
		title: "Home",
		searchUsername:""
	},
	mounted(){
	},
	methods:{
		getAllUsers: function(){
    		axios
    		.get("/Project/rest/users/getAllUsers")
    		.then(response => (this.users = response.data))
    	},
    	search : function(){
    		axios
    		.get("/Project/rest/users/searchUsers", {params: {username : this.searchUsername}})
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
			var u = {username : user.username, password : user.password, firstName : user.firstName, lastName : user.lastName, role:user.role, gender:user.gender};
			axios
	          .post("/Project/rest/users/register", u)
	          .then(response => { 
	        	  if(response.data=="OK")
	        		  (alert("User " + u.firstName + " " + u.lastName + " is successfuly registered."));
	        	  else
	        		  (alert("User with that username already exists!"));
	          })
		}
	}
});