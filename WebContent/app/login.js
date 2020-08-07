var home = new Vue({
	el : '#home',
	data:{
		users:null,
		user:{},
		title: "Home"
	},
	mounted () {
		axios
        .get('/WebProject/rest/users/getAllUsers')
        .then(response => (this.users = response.data))
    },
	methods:{
		login : function(){
			axios
			.post('/WebProject/rest/users/login', this.user)
			.then(alert("Success!"))
			.catch((error) => {console.log(error)});
		},
		cancel : function(){
			axios
			.get('/WebProject/rest/users/findByUsername', this.user.username)
			.then(response => (this.user = reponse.data))
		}
	}
});