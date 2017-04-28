function refreshSpecialtiesList() {
    var facultyId = $("#faculty").val();
    $.ajax('/rest/specialties?facultyId=' + facultyId, {
        method: 'GET',
        async: false,
        success: function (data) {
            var specialtySelector = $('#specialty');
            specialtySelector.empty();
            data.forEach(function (item, i) {
                var option = "<option value = " + item.id + ">" + item.name + "</option>";
                specialtySelector.append(option);
            });
            specialtySelector.change();
        },
        error: function (e) {
            console.log(e)
        }
    });
}

function refreshGroupsList() {
    var specialtyId = $("#specialty").val();
    $.ajax('/rest/groups?specialtyId=' + specialtyId, {
        method: 'GET',
        async: false,
        success: function (data) {
            var groupSelector = $('#group');
            groupSelector.empty();
            data.forEach(function (item, i) {
                var option = "<option value = " + item.id + ">" + item.id + "</option>";
                groupSelector.append(option);
            });
            groupSelector.change();
        },
        error: function (e) {
            console.log(e);
        }
    });
}

function refreshCourse() {
    var groupId = $('#group').val();
    $.ajax('/rest/groups?groupId=' + groupId, {
        method: 'GET',
        async: false,
        success: function (data) {
            $('#course').val(data.course);
        },
        error: function (e) {
            console.log(e);
        }
    });
}