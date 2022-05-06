package cn.vika.client.api;

import java.util.ArrayList;
import java.util.List;

import cn.vika.client.api.http.ApiCredential;
import cn.vika.client.api.model.CreateFieldRequest;
import cn.vika.client.api.model.CreateFieldResponse;
import cn.vika.client.api.model.builder.CreateFieldRequestBuilder;
import cn.vika.client.api.model.field.FieldType;
import cn.vika.client.api.model.field.property.CheckboxFieldProperty;
import cn.vika.client.api.model.field.property.CreatedTimeFieldProperty;
import cn.vika.client.api.model.field.property.CurrencyFieldProperty;
import cn.vika.client.api.model.field.property.DateTimeFieldProperty;
import cn.vika.client.api.model.field.property.EmptyProperty;
import cn.vika.client.api.model.field.property.FormulaFieldProperty;
import cn.vika.client.api.model.field.property.LastModifiedByFieldProperty;
import cn.vika.client.api.model.field.property.LastModifiedTimeFieldProperty;
import cn.vika.client.api.model.field.property.MagicLinkFieldProperty;
import cn.vika.client.api.model.field.property.MagicLookupFieldProperty;
import cn.vika.client.api.model.field.property.MemberFieldProperty;
import cn.vika.client.api.model.field.property.NumberFieldProperty;
import cn.vika.client.api.model.field.property.PercentFieldProperty;
import cn.vika.client.api.model.field.property.RatingFieldProperty;
import cn.vika.client.api.model.field.property.SingleSelectFieldProperty;
import cn.vika.client.api.model.field.property.SingleTextFieldProperty;
import cn.vika.client.api.model.field.property.TextFieldProperty;
import cn.vika.client.api.model.field.property.option.CollectTypeEnum;
import cn.vika.client.api.model.field.property.option.DateFormatEnum;
import cn.vika.client.api.model.field.property.option.Format;
import cn.vika.client.api.model.field.property.option.FormatType;
import cn.vika.client.api.model.field.property.option.NumberFormat;
import cn.vika.client.api.model.field.property.option.PrecisionEnum;
import cn.vika.client.api.model.field.property.option.RollUpFunctionEnum;
import cn.vika.client.api.model.field.property.option.SelectOption;
import cn.vika.client.api.model.field.property.option.SymbolAlignEnum;
import cn.vika.client.api.model.field.property.option.TimeFormatEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static cn.vika.client.api.ConstantKey.TEST_API_KEY;
import static cn.vika.client.api.ConstantKey.TEST_DATASHEET_ID;
import static cn.vika.client.api.ConstantKey.TEST_HOST_URL;
import static cn.vika.client.api.ConstantKey.TEST_SPACE_ID;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author tao
 */
public class FieldAddOperationTest {

    private final String SPACE_ID = TEST_SPACE_ID.get();

    private final String DATASHEET_ID = TEST_DATASHEET_ID.get();

    private final String HOST_URL = TEST_HOST_URL.get();

    private final String API_KEY = TEST_API_KEY.get();

    private final VikaApiClient vikaApiClient = new VikaApiClient(HOST_URL, new ApiCredential(API_KEY));

    private String cacheFieldId;

    @Test
    void testCreateSingleTextField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        SingleTextFieldProperty singleTextFieldProperty = new SingleTextFieldProperty();
        CreateFieldRequest<SingleTextFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.SingleText)
                .withName("singleText")
                .withProperty(singleTextFieldProperty)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("singleText");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateSingleTextFieldWithOtherInfo() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        SingleTextFieldProperty singleTextFieldProperty = new SingleTextFieldProperty();
        singleTextFieldProperty.setDefaultValue("defaultValue");
        CreateFieldRequest<SingleTextFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.SingleText)
                .withName("singleTextWithOtherInfo")
                .withProperty(singleTextFieldProperty)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("singleTextWithOtherInfo");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateTextField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        TextFieldProperty property = new TextFieldProperty();
        CreateFieldRequest<TextFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Text)
                .withName("text")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("text");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateTextFieldWithoutProperty() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        CreateFieldRequest<EmptyProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Text)
                .withName("textWithoutProperty")
                .withoutProperty()
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("textWithoutProperty");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateSingleSelectField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        List<SelectOption> options = new ArrayList<>();
        SelectOption firstOption = new SelectOption();
        firstOption.setName("first");
        SelectOption secondOption = new SelectOption();
        secondOption.setName("second");
        options.add(firstOption);
        options.add(secondOption);
        SingleSelectFieldProperty property = new SingleSelectFieldProperty();
        property.setOptions(options);
        CreateFieldRequest<SingleSelectFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.SingleSelect)
                .withName("singleSelect")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("singleSelect");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateSingleSelectFieldWithOtherInfo() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        List<SelectOption> options = new ArrayList<>();
        SelectOption firstOption = new SelectOption();
        firstOption.setName("first");
        firstOption.setColor("deepPurple_0");
        SelectOption secondOption = new SelectOption();
        secondOption.setName("second");
        options.add(firstOption);
        options.add(secondOption);
        SingleSelectFieldProperty property = new SingleSelectFieldProperty();
        property.setDefaultValue("first");
        property.setOptions(options);
        CreateFieldRequest<SingleSelectFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.SingleSelect)
                .withName("singleSelectWithOtherInfo")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("singleSelectWithOtherInfo");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateNumberField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        NumberFieldProperty property = new NumberFieldProperty();
        property.setPrecision(PrecisionEnum.POINT0);
        CreateFieldRequest<NumberFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Number)
                .withName("number")
                .withProperty(property).build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("number");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateNumberFieldWithOtherInfo() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        NumberFieldProperty property = new NumberFieldProperty();
        property.setPrecision(PrecisionEnum.POINT1);
        property.setDefaultValue("1");
        property.setSymbol("million");
        CreateFieldRequest<NumberFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Number)
                .withName("numberWithOtherInfo")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("numberWithOtherInfo");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateCurrencyField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        CurrencyFieldProperty property = new CurrencyFieldProperty();
        CreateFieldRequest<CurrencyFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Currency)
                .withName("currency")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("currency");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateCurrencyFieldWithOtherInfo() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        CurrencyFieldProperty property = new CurrencyFieldProperty();
        property.setPrecision(PrecisionEnum.POINT0);
        property.setDefaultValue("1");
        property.setSymbol("$");
        property.setSymbolAlign(SymbolAlignEnum.Default);
        CreateFieldRequest<CurrencyFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Currency)
                .withName("currencyWithOtherInfo")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("currencyWithOtherInfo");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreatePercentField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        PercentFieldProperty property = new PercentFieldProperty();
        property.setPrecision(PrecisionEnum.POINT0);
        CreateFieldRequest<PercentFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Percent)
                .withName("percent")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("percent");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreatePercentFieldWithOtherInfo() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        PercentFieldProperty property = new PercentFieldProperty();
        property.setPrecision(PrecisionEnum.POINT0);
        property.setDefaultValue("1");
        CreateFieldRequest<PercentFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Percent)
                .withName("percentWithOtherInfo")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("percentWithOtherInfo");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateDateTimeFieldWithOtherInfo() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        DateTimeFieldProperty property = new DateTimeFieldProperty();
        property.setDateFormat(DateFormatEnum.DATE_SLASH);
        property.setTimeFormat(TimeFormatEnum.HOUR_MINUTE_24);
        property.setAutoFill(true);
        property.setIncludeTime(true);
        CreateFieldRequest<DateTimeFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.DateTime)
                .withName("dateTimeWithOtherInfo")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("dateTimeWithOtherInfo");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateDateTimeField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        DateTimeFieldProperty property = new DateTimeFieldProperty();
        property.setDateFormat(DateFormatEnum.DATE_SLASH);
        CreateFieldRequest<DateTimeFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.DateTime)
                .withName("dateTime")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("dateTime");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateAttachmentField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        CreateFieldRequest<EmptyProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Attachment)
                .withName("attachment")
                .withoutProperty()
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("attachment");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateMemberField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        MemberFieldProperty property = new MemberFieldProperty();
        CreateFieldRequest<MemberFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Member)
                .withName("member")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("member");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateMemberFieldWithOtherInfo() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        MemberFieldProperty property = new MemberFieldProperty();
        property.setMulti(false);
        property.setShouldSendMsg(false);
        CreateFieldRequest<MemberFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Member)
                .withName("memberWithOtherInfo")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("memberWithOtherInfo");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateCheckboxField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        CheckboxFieldProperty property = new CheckboxFieldProperty();
        property.setIcon("sweat_smile");
        CreateFieldRequest<CheckboxFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Checkbox)
                .withName("checkbox")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("checkbox");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateRatingField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        RatingFieldProperty property = new RatingFieldProperty();
        property.setIcon("sweat_smile");
        property.setMax(5);
        CreateFieldRequest<RatingFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Rating)
                .withName("rating")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("rating");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateURLField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        CreateFieldRequest<EmptyProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.URL)
                .withName("url")
                .withoutProperty()
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("url");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreatePhoneField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        CreateFieldRequest<EmptyProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Phone)
                .withName("phone")
                .withoutProperty()
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("phone");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateEmailField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        CreateFieldRequest<EmptyProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Email)
                .withName("email")
                .withoutProperty()
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("email");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateMagicLinkField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        MagicLinkFieldProperty property = new MagicLinkFieldProperty();
        property.setForeignDatasheetId("dstHQGoCG6sAFkrY3t");
        CreateFieldRequest<MagicLinkFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.MagicLink)
                .withName("magicLink")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("magicLink");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateMagicLinkFieldWithOtherInfo() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        MagicLinkFieldProperty property = new MagicLinkFieldProperty();
        property.setForeignDatasheetId("dstHQGoCG6sAFkrY3t");
        property.setLimitToViewId("viwUQiknmXemJ");
        property.setLimitSingleRecord(true);
        CreateFieldRequest<MagicLinkFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.MagicLink)
                .withName("magicLinkWithOtherInfo")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("magicLinkWithOtherInfo");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateMagicLookUpField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        MagicLookupFieldProperty property = new MagicLookupFieldProperty();
        property.setRelatedLinkFieldId("fld2XcVvvXzq5");
        property.setTargetFieldId("fldLxKFAMZYXX");
        CreateFieldRequest<MagicLookupFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.MagicLookUp)
                .withName("magicLookUp")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("magicLookUp");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateMagicLookUpFieldWithRollupFunction() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        MagicLookupFieldProperty property = new MagicLookupFieldProperty();
        property.setRelatedLinkFieldId("fld2XcVvvXzq5");
        property.setTargetFieldId("fldLxKFAMZYXX");
        property.setRollupFunction(RollUpFunctionEnum.SUM);
        CreateFieldRequest<MagicLookupFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.MagicLookUp)
                .withName("magicLookUpWithRollupFunction")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("magicLookUpWithRollupFunction");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateFormalField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        FormulaFieldProperty formulaFieldProperty = new FormulaFieldProperty();
        formulaFieldProperty.setExpression("1 / 3");
        Format<NumberFormat> numberFormatFormat = new Format<>();
        numberFormatFormat.setType(FormatType.Number);
        NumberFormat numberFormat = new NumberFormat();
        numberFormat.setPrecision(3);
        numberFormatFormat.setFormat(numberFormat);
        formulaFieldProperty.setFormat(numberFormatFormat);
        CreateFieldRequest<FormulaFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.Formula)
                .withName("formula")
                .withProperty(formulaFieldProperty)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("formula");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateAutoNumberField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        CreateFieldRequest<EmptyProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.AutoNumber)
                .withName("autoNumber")
                .withoutProperty()
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("autoNumber");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreatedTimeField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        CreatedTimeFieldProperty property = new CreatedTimeFieldProperty();
        property.setDateFormat(DateFormatEnum.DATE_SLASH);
        CreateFieldRequest<CreatedTimeFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.CreatedTime)
                .withName("createdTime")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("createdTime");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreatedTimeFieldWithOtherInfo() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        CreatedTimeFieldProperty property = new CreatedTimeFieldProperty();
        property.setDateFormat(DateFormatEnum.DATE_SLASH);
        property.setTimeFormat(TimeFormatEnum.HOUR_MINUTE_24);
        property.setIncludeTime(true);
        CreateFieldRequest<CreatedTimeFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.CreatedTime)
                .withName("createdTimeWithOtherInfo")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("createdTimeWithOtherInfo");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateLastModifiedTimeFieldWithRemindAllField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        LastModifiedTimeFieldProperty property = new LastModifiedTimeFieldProperty();
        property.setDateFormat(DateFormatEnum.DATE_SLASH);
        property.setCollectType(CollectTypeEnum.ALL);
        CreateFieldRequest<LastModifiedTimeFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.LastModifiedTime)
                .withName("lastModifiedTime")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("lastModifiedTime");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateLastModifiedTimeFieldWithRemindSpecifiedField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        LastModifiedTimeFieldProperty property = new LastModifiedTimeFieldProperty();
        property.setDateFormat(DateFormatEnum.DATE_SLASH);
        property.setCollectType(CollectTypeEnum.SPECIFIED);
        List<String> fieldIds = new ArrayList<>();
        fieldIds.add("fld00fkxDmSfj");
        property.setFieldIdCollection(fieldIds);
        CreateFieldRequest<LastModifiedTimeFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.LastModifiedTime)
                .withName("lastModifiedTimeWithRemindSpecifiedField")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("lastModifiedTimeWithRemindSpecifiedField");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateCreatedByField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        CreateFieldRequest<EmptyProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.CreatedBy)
                .withName("createdByField")
                .withoutProperty()
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("createdByField");
        cacheFieldId = response.getId();
    }

    @Test
    void testCreateLastModifiedByFieldField() {
        FieldApi fieldApi = vikaApiClient.getFieldApi();
        LastModifiedByFieldProperty property = new LastModifiedByFieldProperty();
        property.setCollectType(CollectTypeEnum.ALL);
        CreateFieldRequest<LastModifiedByFieldProperty> request = CreateFieldRequestBuilder
                .create()
                .ofType(FieldType.LastModifiedBy)
                .withName("LastModifiedBy")
                .withProperty(property)
                .build();
        CreateFieldResponse response = fieldApi.addField(SPACE_ID, DATASHEET_ID, request);
        assertThat(response.getName()).isEqualTo("LastModifiedBy");
        cacheFieldId = response.getId();
    }

    @AfterEach
    void tearDown() {
        if (cacheFieldId != null) {
            FieldApi fieldApi = vikaApiClient.getFieldApi();
            fieldApi.deleteField(SPACE_ID, DATASHEET_ID, cacheFieldId);
        }
    }

}
