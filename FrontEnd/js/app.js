/** Defining the endpoint **/
var endpoint = "http://35.187.194.28:8080/server/";

/** Utility function to send data **/
function post(route, data, $http, callback) {
    $http({
            method: 'POST',
            url: endpoint + route,
            headers: {'Content-Type': 'application/json'},
            data: JSON.stringify(params)
        }).success(function(data, status, headers, config) {
            callback(data);
        })
        .error(function(data, status, header, config) {
            callback(data);
        });
}

/** Utility function to get data **/
function get(route, params, $http, callback) {
    var config = {
        params: params
    };

    $http.get(endpoint + route, config)
        .success(function(data, status, headers, config) {
            callback(data);
        })
        .error(function(data, status, header, config) {
            callback(data);
        });
}

/** Utilitis for form verification **/
function checkEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function checkEmpty(str) {
    return str.length > 0;
}

function checkPassword(pwd, cpwd) {
    return pwd.length > 4 && pwd == cpwd;
}

/** The app starts here **/
var app = angular.module('PartyList', [])
    .controller('LoginRegisterCtrl', function($scope, $http) {
        /** Initializing Login/ Register Objects **/
        $scope.LoginObject = {};
        $scope.RegisterObject = {};
        $scope.message_login = "";
        $scope.message_register = "";

        /** Login function **/
        $scope.login = function() {
            email = $scope.LoginObject.email;
            pwd = $scope.LoginObject.password;
            if (!checkEmail(email) || !checkEmpty(pwd))
                $scope.message_login = 'Incorrect login information.';
            else {
                params = {email: email, password: pwd };
                post('users/login', params, $http, function(data) {
                    console.log(data);
                    if (data.code == 200)
                        $scope.message_login =  'Login successful!';
                    else
                        $scope.message_login = 'Login unsuccessful, please enter valid credentials!';
                });
            }
        }

        /** Register function **/
        $scope.register = function() {
            name = $scope.RegisterObject.name;
            pwd = $scope.RegisterObject.password;
            cpwd = $scope.RegisterObject.confirm_password;
            uemail = $scope.RegisterObject.email;

            if (!checkEmail(uemail) || !checkEmpty(name) ||
                !checkPassword(pwd, cpwd))
                $scope.message_register =  'Incorrect user information.';
            else {
                params = { name: name, password: pwd, email: uemail };
                post('users/register', params, $http, function(data) {
                    console.log(data);
                    if (data.code == 200)
                       $scope.message_register = 'Registration successful!';
                    else
                        $scope.message_register = 'Registration unsuccessful, please enter valid credentials!';
                });
            }
        }
    })
    .directive('ngEnter', function() {
        return function(scope, element, attrs) {
            element.bind("keydown keypress", function(event) {
                if (event.which === 13) {
                    scope.$apply(function() {
                        scope.$eval(attrs.ngEnter);
                    });

                    event.preventDefault();
                }
            });
        };
    });
