package com.cs309.loginscreen.Model;

/**
 * Verification Listener for the project. Implement this if you want
 * to listen to Verification response.
 *
 * @author Bofu
 */
public interface IVerificationListener {

    /**
     * On Success of the Verification:
     * The userName and PassW PASS the verification
     * from the server.
     * @param response - Success message
     */
    void onSuccess(String response);

    /**
     * On failure of the verification
     * It means that the userName or PassW DID NOT PASS
     * the server verification.
     * @param response - failure message
     */
    void onFailure(String response);
}
