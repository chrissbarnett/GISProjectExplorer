/**
 * Created by cbarne02 on 5/11/15.
 */

(function() {

    //TODO: make this controller a directive so we can reuse it?
    //container controller for the modal dialog
    angular.module("explorerControllers").controller("CoursesCtrl", ['$scope', '$modal', 'courseService', function ($scope, $modal, apiService) {
        $scope.config = {
            service: "courseService",
            modal_form: {
                item_label: "Course",
                templateUrl: 'templates/addCourse.html',
                controller_name: "CourseFormCtrl",
                columnDefs: [
                    {name: 'name'},
                    {name: 'semester', width: 100},
                    {name: 'year', width: 100},
                    {name: 'instructor'}]
            }
        };

        //options for ui.grid
        $scope.gridOptions = {};

        var columnDefs = [
            {
                name: 'edit',
                width: 100,
                cellTemplate: '<button class="btn btn-default btn-sm" ng-click="grid.appScope.editItem(row.entity)">edit</button>'
            },
            {
                name: 'delete',
                width: 100,
                cellTemplate: '<button class="btn btn-danger btn-sm" ng-click="grid.appScope.deleteItem(row.entity)">X</button>'
            }
        ];

        $scope.gridOptions.columnDefs = $scope.config.modal_form.columnDefs.concat(columnDefs);


        $scope.editItem = function (item) {
            $scope.openForm('edit', item).then(function (result) {
                console.log(result); //error handling?
                //on modal dismiss, item list should update
                apiService.update(result.item).success(function (data) {
                    console.log(data);
                    $scope.getItems();
                });

            }, function () {
                console.log('Modal dismissed at: ' + new Date());
            });
        };

        $scope.createItem = function () {
            $scope.openForm('create').then(function (result) {
                console.log(result); //error handling?
                //on modal dismiss, item list should update
                apiService.create(result.item).success(function (response) {
                    $scope.getItems();
                });


            }, function () {
                console.log('Modal dismissed at: ' + new Date());
            });


        };

        $scope.deleteItem = function (item) {
            apiService.delete(item).success(function (data) {
                console.log(data);
                $scope.getItems();
            });
        };

        $scope.getItems = function () {
            apiService.findAll().success(function (response) {
                //callback from the service populates the ui-grid data array

                $scope.gridOptions.data = response;
            });
        };


        $scope.formText = {
            display: {},
            edit: {
                header: "Edit " + $scope.config.modal_form.item_label
            },
            create: {
                header: "Create a New " + $scope.config.modal_form.item_label
            }
        };

        //initial population of list
        $scope.getItems();

        $scope.openForm = function (mode, item) {
            $scope.mode = mode;
            $scope.editedItem = {}; //initialize the object for editing
            if (mode === 'edit') {
                //we need to inject the course values into the form ( a copy of the object )
                if (typeof item !== "undefined") {
                    $scope.currentItem = item;
                    angular.copy(item, $scope.editedItem);
                } else {
                    throw new Error("'item' parameter missing!");
                }

            } else if (mode === 'create') {
                $scope.currentItem = {};
            }

            $scope.formText.display.header = $scope.formText[mode].header;


            $scope.modalInstance = $modal.open({
                templateUrl: $scope.config.modal_form.templateUrl,
                controller: $scope.config.modal_form.controller_name,
                scope: $scope
            });


            return $scope.modalInstance.result;
        };


        //buttons
        $scope.save = function () {
            //do some validation? test to see if edited course differs from current course?
            $scope.modalInstance.close({success: true, item: $scope.editedItem});

        };

        $scope.reset = function () {
            $scope.editedItem = {};
        };

        $scope.cancel = function () {
            $scope.modalInstance.dismiss('cancelled');
        };

    }]);

})();
