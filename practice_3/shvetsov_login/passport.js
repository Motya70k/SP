const passport = require('passport');
const GoogleStrategy = require('passport-google-oauth2').Strategy;
const GitHubStrategy = require('passport-github2').Strategy;

passport.serializeUser((user , done) => {
    done(null , user);
})
passport.deserializeUser(function(user, done) {
    done(null, user); 
});

// passport.use(new GoogleStrategy({
//     callbackURL:"/auth/google/callback",
//     passReqToCallback:true
//     },
//     function(request, accessToken, refreshToken, profile, done) {
//         return done(null, profile);
//     }
// ));

passport.use(new GitHubStrategy({
    clientID: "",
    clientSecret: "",
    callbackURL: "/auth/github/callback"
  },
  function(accessToken, refreshToken, profile, done) {
    return done(null, profile)
  }
));
