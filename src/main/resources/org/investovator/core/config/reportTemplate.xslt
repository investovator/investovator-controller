<xsl:stylesheet version="2.0"  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


    <xsl:template match="node() | @*">
        <xsl:copy>
            <xsl:apply-templates select="node() | @*" />
        </xsl:copy>
    </xsl:template>


    <xsl:template match="@id">
        <xsl:attribute name="id">
            <xsl:value-of select="replace(.,'\$Stock','GOOG')"/>
        </xsl:attribute>
    </xsl:template>

    <xsl:template match="@ref">
        <xsl:attribute name="ref">
            <xsl:value-of select="replace(.,'\$Stock','GOOG')"/>
        </xsl:attribute>
    </xsl:template>



</xsl:stylesheet>