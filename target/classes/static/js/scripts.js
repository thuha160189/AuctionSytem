/*!
    * Start Bootstrap - SB Admin v6.0.1 (https://startbootstrap.com/templates/sb-admin)
    * Copyright 2013-2020 Start Bootstrap
    * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
    */
(function ($) {
    "use strict";

    // Add active state to sidebar nav links
    var path = window.location.href; // because the 'href' property of the DOM element is the absolute path
    $("#layoutSidenav_nav .sb-sidenav a.nav-link").each(function () {
        if (this.href === path) {
            $(this).addClass("active");
        }
    });

    // Toggle the side navigation
    $("#sidebarToggle").on("click", function (e) {
        e.preventDefault();
        $("body").toggleClass("sb-sidenav-toggled");
    });
    // var win = $(window);
    // var doc = $(document);
    //
    // // Each time the user scrolls
    // win.scroll(function() {
    //     // Vertical end reached?
    //     if (doc.height() - win.height() == win.scrollTop()) {
    //         // New row
    //         var tr = $('<tr />').append($('<th />')).appendTo($('#dataTable'));
    //
    //         // Current number of columns to create
    //         var n_cols = $('#dataTable tr:first-child th').length;
    //         for (var i = 0; i < n_cols; ++i)
    //             tr.append($('<td />'));
    //     }
    //
    //     // Horizontal end reached?
    //     if (doc.width() - win.width() == win.scrollLeft()) {
    //         // New column in the heading row
    //         $('#dataTable tr:first-child').append($('<th />'));
    //
    //         // New column in each row
    //         $('#dataTable tr:not(:first-child)').each(function() {
    //             $(this).append($('<td />'));
    //         });
    //     }
    // });
})(jQuery);
