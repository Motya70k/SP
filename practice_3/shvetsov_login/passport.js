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
    callbackURL:"/auth/google/callback",
    passReqToCallback:true
    },
    function(request, accessToken, refreshToken, profile, done) {
        return done(null, profile);
    }
));

passport.use(new GitHubStrategy({
    clientID: "dc59d957ba23e7a686fb",
    clientSecret: "f28d4f7072c900539fb295e385fe7e2d32c8e5e9",
    callbackURL: "http://localhost:3000/auth/github/callback"
  },
  function(accessToken, refreshToken, profile, done) {
    return done(null, profile)
  }
));
