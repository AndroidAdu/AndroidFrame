
package com.dxy.facebook.login;


import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 获取FB用户信息回调
 *
 */
public class GetFBUserCallback {

    public interface IGetUserResponse {
        void onCompleted(FBUser user);
    }

    private IGetUserResponse mGetUserResponse;
    private GraphRequest.Callback mCallback;

    public GetFBUserCallback(final IGetUserResponse getUserResponse) {

        mGetUserResponse = getUserResponse;
        mCallback = new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                FBUser user = null;
                try {
                    JSONObject userObj = response.getJSONObject();
                    if (userObj == null) {
                        return;
                    }
                    user = jsonToUser(userObj);

                } catch (JSONException e) {
                    // Handle exception ...
                }

                // Handled by ProfileActivity
                mGetUserResponse.onCompleted(user);
            }
        };
    }

    private FBUser jsonToUser(JSONObject user) throws JSONException {
//        Uri picture = Uri.parse();
        String picture=user.getJSONObject("picture").getJSONObject("data").getString
                ("url");
        String name = user.getString("name");
        String id = user.getString("id");
        String email = null;
        if (user.has("email")) {
            email = user.getString("email");
        }

        //暂时不需要permission

        // Build permissions display string
//        StringBuilder builder = new StringBuilder();
//        JSONArray perms = user.getJSONObject("permissions").getJSONArray("data");
//        builder.append("Permissions:\n");
//        for (int i = 0; i < perms.length(); i++) {
//            builder.append(perms.getJSONObject(i).get("permission")).append(": ").append(perms
//                    .getJSONObject(i).get("status")).append("\n");
//        }
//        String permissions = builder.toString();

        return new FBUser(picture, name, id, email);
    }

    public GraphRequest.Callback getCallback() {
        return mCallback;
    }
}
