/*exports.area = function(width) 
{ return width * width; 
};
exports.perimeter = function(width)
 { 
 return 4 * width; 
 };
 
 */
 
 //Alternative way of exporting
 module.exports = {
  area: function(width) {
    return width * width;
  },

  perimeter: function(width) {
    return 4 * width;
  }
};