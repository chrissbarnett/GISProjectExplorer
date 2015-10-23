/**
 * Created by cbarne02 on 5/11/15.
 */


(function() {

    //controller for the modal dialog/form
    angular.module("explorerControllers").controller("ProjectFormCtrl", ['$scope', '$modalInstance', function ($scope, $modalInstance) {
        //drop down values
        $scope.availableSemesters = ['fall', 'spring', 'summer'];

        var generateYears = function () {
            var current = new Date().getFullYear();
            var years = [];
            var minYear = 2000;
            while (current >= minYear) {
                years.push(current.toString());
                current--;
            }
            return years;
        };

        $scope.availableYears = generateYears();


    }]);

})();
