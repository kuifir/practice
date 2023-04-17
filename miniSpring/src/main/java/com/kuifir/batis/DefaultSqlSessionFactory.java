package com.kuifir.batis;

import com.kuifir.beans.factory.annotation.Autowired;
import com.kuifir.jdbc.core.JDBCTemplate;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    @Autowired
    private JDBCTemplate jdbcTemplate;
    private String mapperLocations;
    private final Map<String, MapperNode> mapperNodeMap = new HashMap<>();

    public void init() {
        scanLocation(this.mapperLocations);
    }


    private void scanLocation(String location) {
        String sLocationPath = Objects.requireNonNull(this.getClass().getClassLoader().getResource(location)).getPath();
        File dir = new File(sLocationPath);
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) { //递归扫描
                scanLocation(location + "/" + file.getName());
            } else { //解析mapper文件
                buildMapperNodes(location + "/" + file.getName());
            }
        }
    }


    private Map<String, MapperNode> buildMapperNodes(String filePath) {
        SAXReader saxReader = new SAXReader();
        URL xmlPath = this.getClass().getClassLoader().getResource(filePath);

        Document document = null;
        try {
            document = saxReader.read(xmlPath);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        Element rootElement = document.getRootElement();

        String namespace = rootElement.attributeValue("namespace");

        Iterator<Element> nodes = rootElement.elementIterator();

        while (nodes.hasNext()) { //对每一个sql语句进行解析
            Element node = nodes.next();
            String id = node.attributeValue("id");
            String parameterType = node.attributeValue("parameterType");
            String resultType = node.attributeValue("resultType");
            String sql = node.getText();

            MapperNode selectnode = new MapperNode();
            selectnode.setNamespace(namespace);
            selectnode.setId(id);
            selectnode.setParameterType(parameterType);
            selectnode.setResultType(resultType);
            selectnode.setSql(sql);
            selectnode.setParameter("");

            this.mapperNodeMap.put(namespace + "." + id, selectnode);
        }
        return this.mapperNodeMap;
    }


    @Override
    public SqlSession openSession() {
        SqlSession newSqlSession = new DefaultSqlSession();
        newSqlSession.setJdbcTemplate(jdbcTemplate);
        newSqlSession.setSqlSessionFactory(this);
        return newSqlSession;
    }

    @Override
    public MapperNode getMapperNode(String name) {
        return this.mapperNodeMap.get(name);
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }
}
