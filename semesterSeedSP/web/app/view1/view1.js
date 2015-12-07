'use strict';

angular.module('myApp.view1', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view1', {
                    templateUrl: 'app/view1/view1.html',
                    controller: 'View1Ctrl',
                    controllerAs: 'ctrl'
                });
            }])



        .controller('View1Ctrl', ["InfoFactory", "InfoService", "$http", function (InfoFactory, InfoService, $http) {
                this.msgFromFactory = InfoFactory.getInfo();
                this.msgFromService = InfoService.getInfo();

                var self = this;
                self.flights; //[{"date": "2016", "airline": "Captana", "flightId": "Hej", "numberOfSeats": "4", "totalPrice": "1234kr", "traveltime": "4232", "origin": "LON"}, {"date": "2016", "airline": "Captana", "flightId": "Sut", "numberOfSeats": "4", "totalPrice": "1234kr", "traveltime": "4232min", "origin": "LON"}, {"date": "2016", "airline": "Captana", "flightId": "sovs", "numberOfSeats": "4", "totalPrice": "1234kr", "traveltime": "4232min", "origin": "LON"}];
                self.date;
                self.dest;
                self.origin;
                self.seats;  
//                var year = self.date.getFullYear();
//                var month = self.date.getMonth();
//                var day = self.date.getDate();
//                self.newDate = new Date(year, month, day, 1);
                self.search = function () {
                    $http({
                        method: 'Get',
                        url: 'api/Scraper/Airlines/' + self.origin + '/' + self.dest + '/' + self.date.toISOString().split('T')[0] + 'T00:00:00.000Z' + '/' + self.seats
                    }).then(
                            function (data) {
                                self.flights = data.data
                            },
                            function (data) {
                            })
                };
            }]);