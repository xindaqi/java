package high.level.rest;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 *
 * @Description: 获取Java High Level REST Client客户端
 * @author xdq
 * @date 2019年9月4日
 *
 */
public class InitDemo {

    public static RestHighLevelClient getClient() {

        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));

        return client;
    }
}