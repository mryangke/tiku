/**
 * 题库外部接口
 * 题库系统供其他应用调用的接口
 *
 * OpenAPI spec version: 1.1.0
 * Contact: czfshine@outlook.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

export interface CollectionInfo { 
    /**
     * 题库对应的id，从管理平台获取
     */
    poolId: string;
    /**
     * 验证用户权限的OAuth令牌，从管理平台申请，暂时无用
     */
    token: string;
    title: string;
    problemIds: Array<string>;
    tags?: Array<string>;
}