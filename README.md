# OkMultitaskDown
文件下载
```
OkManager.getInstance().get().url(url2).enqueue(new FileCallBack(Environment.getExternalStorageDirectory().getPath()) {
            @Override
            public void onProgress(long totalLength, long alreadyDownLength) {
                Log.d(TAG, "onProgress: totalLength:"+totalLength+"   alreadyDownLength:"+alreadyDownLength);
            }
            @Override
            public void onSuccess(String msg) {
                Log.d(TAG, "onSuccess: "+msg);
            }
            @Override
            public void onFail(String msg, Exception e) {
                Log.d(TAG, "onFail: "+msg);
            }
        });
```
get请求
``
OkManager.getInstance().get().url(url2).header(header).params(params).enqueue(new StringCallBack() {
           @Override
           public void onSuccess(String msg) {
               Log.d(TAG, "onSuccess: "+msg);
           }
           @Override
           public void onFail(String msg, Exception e) {
               Log.d(TAG, "onFail: "+msg);
           }
       });
``