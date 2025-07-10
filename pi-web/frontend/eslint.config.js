// @ts-check
const eslint = require("@eslint/js");
const tseslint = require("typescript-eslint");
const angular = require("angular-eslint");
const importPlugin = require('eslint-plugin-import');
const unusedImports = require("eslint-plugin-unused-imports");

module.exports = tseslint.config(
  {
    plugins: {
      "unused-imports": unusedImports,
    },
    files: ["**/*.ts"],
    extends: [
      eslint.configs.recommended,
      ...tseslint.configs.recommended,
      ...tseslint.configs.stylistic,
      ...angular.configs.tsRecommended,
      // @ts-ignore
      importPlugin.flatConfigs.recommended,
    ],
    languageOptions: {
      ecmaVersion: 'latest',
      sourceType: 'module',
    },
    "settings": {
      "import/resolver": {
        "typescript": true,
        "node": true,
      },
    },
    processor: angular.processInlineTemplates,
    rules: {
      'import/no-dynamic-require': 'warn',
      'import/no-nodejs-modules': 'warn',
      'import/order': ['error', {
        groups: [
          "index",
          "sibling",
          "parent",
          "internal",
          "external",
          "builtin",
          "object",
          "type",
        ], alphabetize: {
          order: 'asc',
          caseInsensitive: true,
        },
        "newlines-between": "always",
        pathGroups: [
          {
            pattern: "@angular/**",
            group: "index",
          },
          {
            pattern: "rxjs",
            group: "index",
            position: "after",
          },
          {
            pattern: "chart.js",
            group: "sibling",
          },
          {
            pattern: "ng2-charts",
            group: "sibling",
            position: "after",
          },
        ], pathGroupsExcludedImportTypes: [
          "@angular",
          "primeng",
          "rxjs",
          "ngx-markdown"
        ],
        "distinctGroup": false
      }],
      "@angular-eslint/directive-selector": [
        "error",
        {
          type: "attribute",
          prefix: "app",
          style: "camelCase",
        },
      ],
      "@angular-eslint/component-selector": [
        "error",
        {
          type: "element",
          prefix: "app",
          style: "kebab-case",
        },
      ],
      "unused-imports/no-unused-imports": "error",
      "unused-imports/no-unused-vars": [
        "warn",
        {
          "vars": "all",
          "varsIgnorePattern": "^_",
          "args": "after-used",
          "argsIgnorePattern": "^_",
        },
      ],
    },
  },
  {
    files: ["**/*.html"],
    extends: [
      ...angular.configs.templateRecommended,
      ...angular.configs.templateAccessibility,
    ],
    rules: {},
  },
);
