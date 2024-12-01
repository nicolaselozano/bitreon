import { Router } from "express";
import passport from "passport";
import {GoogleAuthController} from "../controllers/GoogleAuthController";

const router = Router();

router.get(
  "/google",
  passport.authenticate("google", { scope: ["profile", "email"] })
);

router.get(
  "/google/callback",
  passport.authenticate("google", { session: false }),
  GoogleAuthController.googleCallback
);

export default router;
