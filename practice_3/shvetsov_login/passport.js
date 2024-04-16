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
    clientID:"787150806272-5ds4tmacgfaoj4l8fl1fms072tshshc5.apps.googleusercontent.com", // Данные из вашего аккаунта.
    clientSecret:"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", // Данные из вашего аккаунта.
    callbackURL:"http://localhost:3000/auth/google/callback",
    passReqToCallback:true
    },
    function(request, accessToken, refreshToken, profile, done) {
        return done(null, profile);
    }
));

passport.use(new GitHubStrategy({
    clientID: "dc59d957ba23e7a686fb",
    clientSecret: "3236ce9e4b1342342fe433b442f18ce4bbfdaf32",
    callbackURL: "http://localhost:3000/auth/github/callback"
  },
  function(accessToken, refreshToken, profile, done) {
    return done(null, profile)
  }
));
