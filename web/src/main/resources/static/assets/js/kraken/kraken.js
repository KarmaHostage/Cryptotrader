var Kraken = function () {
    return {
        fetchPrice: function (pair, response) {
            var url = 'https://api.kraken.com/0/public/Ticker?pair=' + pair.name;
            $.get(url, function (returnValue) {
                response(returnValue);
            });
        },
        fillPrice: function(pair, $_element) {
            this.fetchPrice(pair, function(returnValue) {
                $_element.html(returnValue.result[pair.result].b[0]);
            });
        }
    }
}();