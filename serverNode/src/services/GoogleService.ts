import axios from "axios";
import config from "../config/config";
import { AuthUserDTO } from "../utils/DTO/AuthUserDTO";

export const findOrCreateUser = async (profile: any) => {
  const url = `${config.API_JAVA}/users/login?` +
    `username=${profile.displayName}` +
    `&email=${profile.emails?.[0]?.value}` +
    `&password=${"google"}`;

  try {
    const { data }: { data: AuthUserDTO } = await axios.get(url);
    console.log("User found:", data);
    return data;
  } catch (error: any) {
    if (error.response?.status === 404) {
      console.log("No user found, creating new user.");
      try {
        const { data: newUser }: { data: AuthUserDTO } = await axios.post(`${config.API_JAVA}/users/register`, {
          email: profile.emails?.[0]?.value,
          password: "google",
          username: profile.displayName
        });
        return newUser;
      } catch (registrationError) {
        throw new Error(`Error during user registration: ${registrationError}`);
      }
    } else {
      // Manejo de errores inesperados
      throw new Error(`Unexpected error during user lookup: ${error.message}`);
    }
  }
};

export const GoogleService = {
  findOrCreateUser,
};
