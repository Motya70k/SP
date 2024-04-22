const passport = require('passport');
const GoogleStrategy = require('passport-google-oauth2').Strategy;
const GitHubStrategy = require('passport-github2').Strategy;

passport.serializeUser((user , done) => {
    done(null , user);
})
passport.deserializeUser(function(user, done) {
    done(null, user); 
});

passport.use(new GoogleStrategy({
    clientID:"", // Данные из вашего аккаунта.
    clientSecret:"", // Данные из вашего аккаунта.
    callbackURL:"/auth/google/callback",
    passReqToCallback:true
    },
    function(request, accessToken, refreshToken, profile, done) {
        return done(null, profile);
    }
));

passport.use(new GitHubStrategy({
    clientID: "",
    clientSecret: "",
    callbackURL: "http://localhost:3000/auth/github/callback"
  },
  function(accessToken, refreshToken, profile, done) {
    return done(null, profile)
  }
));
