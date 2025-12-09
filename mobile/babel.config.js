module.exports = function(api) {
  api.cache(true);
  return {
    presets: ['babel-preset-expo'],
    plugins: [
      // Aici nu mai trebuie sa fie nimic legat de reanimated
    ],
  };
};