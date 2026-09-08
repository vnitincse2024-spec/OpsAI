/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        emerald: {
          50: '#effcf6',
          100: '#c6f7e2',
          200: '#8eedcc',
          300: '#65d6ab',
          400: '#3ebd93',
          500: '#27ae83', // primary accent
          600: '#199473',
          700: '#147d64',
          800: '#0c664c',
          900: '#053527',
        },
        darkbg: '#0a0a0a',
      },
      backdropBlur: { xs: '2px' },
      boxShadow: {
        glass: '0 4px 30px rgba(0,0,0,0.5)',
      },
      borderRadius: { lg: '1rem' },
    },
  },
  plugins: [],
};
