const path = require('path');
const {CleanWebpackPlugin} = require('clean-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const {args2options} = require('peacetrue-js/src/node');
let options = args2options(process.argv, '-pt:');
//生成一个源文件、一个压缩文件和一个source map 文件

let config = {
    mode: 'development',
    entry: "./index.js",
    devtool: 'source-map',
    plugins: [
        new CleanWebpackPlugin(),
    ],
    devServer: {
        contentBase: './'
    },
    output: {
        path: path.resolve(__dirname, 'dist'),
        filename: "peace.async-validator.js",
        library: ['Peace', 'AsyncValidator'],
        libraryExport: '',
        libraryTarget: 'umd',
        globalObject: 'this',
    },
    externals: {
        'axios': 'axios',
        'peacetrue-js/src/core': {
            root: ['Peace', 'Core'],
            commonjs: 'peacetrue-js/src/core',
            commonjs2: 'peacetrue-js/src/core',
            amd: 'peacetrue-js/src/core'
        },
    }
};

/* prefix:--
plugins|p=html,clean  //指定使用的插件
*/
function formatAlias(options, alias) {
    Object.keys(alias).forEach(key => {
        if (key in options) {
            options[alias[key]] = options[key];
        }
    });
}

formatAlias(options, {p: 'plugins'});
if (options.plugins && options.plugins.indexOf('html') > -1) {
    config.plugins.push(new HtmlWebpackPlugin({
            title: 'Test',
            inject: 'head',
            template: 'test/index.ejs'
        })
    );
}
module.exports = config;