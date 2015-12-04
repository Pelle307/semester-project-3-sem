'use strict';

angular.module('myApp.view1', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view1', {
                    templateUrl: 'view1/view1.html',
                    controller: 'View1Ctrl',
                    controllerAs: 'ctrl'
                });
            }])



        .controller('View1Ctrl', ["$http","InfoFactory", "InfoService", function ($http, InfoFactory, InfoService) {
                this.msgFromFactory = InfoFactory.getInfo();
                this.msgFromService = InfoService.getInfo();

                var self = this;
                self.flights = [{"date": "2016", "airline": "Captana", "flightId":"Hej", "numberOfSeats": "4", "totalPrice": "1234kr", "traveltime": "4232", "origin": "LON"}, {"date": "2016", "airline": "Captana", "flightId":"Sut", "numberOfSeats": "4", "totalPrice": "1234kr", "traveltime": "4232min", "origin": "LON"}, {"date": "2016", "airline": "Captana", "flightId":"sovs", "numberOfSeats": "4", "totalPrice": "1234kr", "traveltime": "4232min", "origin": "LON"}];
                self.date;
                self.dest;
                self.origin;
                self.seats;
                self.search = function(){
                    $http({
                        method:'Get',
                        url: 'http://angularairline-plaul.rhcloud.com/api/flightinfo/'+self.origin+'/'+self.dest + '/' + self.date + '/' + self.seats
                    }).then(
                            function(data){self.flights=data.data},
                    function(data){})};
            }]);